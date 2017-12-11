package com.example.nicoslaptop.mindblaster;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import database.DataProcess;
import database.PuzzleDatabase;

public class Homescreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        FirstRun();

        Button play = (Button)findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loadQuestion = new Intent(Homescreen.this,Question.class);
                Homescreen.this.startActivity(loadQuestion);
            }
        });
    }

    private void FirstRun() {

        SharedPreferences settings = this.getSharedPreferences("MindBlaster", 0);
        boolean firstrun = settings.getBoolean("firstrun", true);
        if (firstrun) { // Checks to see if we've ran the application b4
            System.out.println("FIRST RUN!");
            SharedPreferences.Editor e = settings.edit();
            e.putBoolean("firstrun", false);
            e.putInt("highscore",0);
            e.commit();

            PuzzleDatabase db = Room.databaseBuilder(getApplicationContext(),PuzzleDatabase.class, "Puzzle_db").fallbackToDestructiveMigration().build();
            DataProcess DAO = new DataProcess(db);

            //LOAD DATABASE RAW DATA
            String json = null;
            try {
                InputStream is = getResources().openRawResource(R.raw.puzzles);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
                json=null;
            }
            System.out.println("JSON: "+ json);
            DAO.fillDatabase(json); // creates the puzzles on first run

            TextView highscore = (TextView)findViewById(R.id.highscore);
            highscore.setText("Highscore: 0");
            //Set up DB
            //set up highscore

        } else { // Otherwise start the application here:
            //load highscore from SharedPreferences and add to box
            int score = settings.getInt("highscore",0);
            TextView highscore = (TextView)findViewById(R.id.highscore);
            highscore.setText("Highscore: "+ score);
        }
    }
}
