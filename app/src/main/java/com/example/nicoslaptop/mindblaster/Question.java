package com.example.nicoslaptop.mindblaster;

import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import database.DataProcess;
import database.Puzzle;
import database.PuzzleDatabase;

public class Question extends AppCompatActivity {
    Puzzle current_puzzle;
    PuzzleDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db= Room.databaseBuilder(getApplicationContext(),PuzzleDatabase.class, "Puzzle_db").fallbackToDestructiveMigration().build();
        super.onCreate(savedInstanceState);
        new AsyncTask<Void, Void, Puzzle>() {
            @Override
            protected Puzzle doInBackground(Void... params) {
                return db.PuzzleDAO().getSingleRecord();
            }

            @Override
            protected void onPostExecute(Puzzle p) {
                final Puzzle x=p;
                setPuzzle(p);
                if(p!=null){
                    TextView name = (TextView) findViewById(R.id.question);
                    EditText answer = (EditText) findViewById(R.id.answer);
                    ImageView image = (ImageView) findViewById(R.id.image);
                    TextView question = (TextView) findViewById(R.id.description);
                    question.setMovementMethod(new ScrollingMovementMethod());
                    Button back = (Button) findViewById(R.id.quit);
                    Button submit= (Button) findViewById(R.id.submit);

                    name.setText(p.getPuzzle_name());

                    String PACKAGE_NAME = getApplicationContext().getPackageName();
                    int imgId = getResources().getIdentifier(PACKAGE_NAME+":raw/"+p.getImage_loc() , null, null);
                    System.out.println("image: "+p.getImage_loc()+" id "+imgId);
                   image.setImageResource(imgId);


                    question.setText(p.getQuestion_text());
                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            EditText inputText = (EditText) findViewById(R.id.answer);
                            String input = inputText.getText().toString().trim();
                            if(input.equalsIgnoreCase(x.getAnswer())){
                                System.out.println("CORRECT!");
                                completePuzzle();
                                AlertDialog.Builder builder = new AlertDialog.Builder(Question.this);
                                builder.setMessage(x.getSolution())
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                Intent loadQuestion = new Intent(Question.this,Question.class);
                                                Question.this.startActivity(loadQuestion);
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(Question.this);
                                builder.setMessage("Sorry, that is incorrect")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.dismiss();
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }

                        }
                    });
                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent loadHome = new Intent(Question.this,Homescreen.class);
                            Question.this.startActivity(loadHome);
                        }
                    });


                }else{
                    Intent loadHome = new Intent(Question.this,Homescreen.class);
                    Question.this.startActivity(loadHome);
                }

            }
        }.execute();


        setContentView(R.layout.activity_question);
    }
    public final void setPuzzle(Puzzle p){
        current_puzzle = p;
    }
    public final void completePuzzle(){
        current_puzzle.setSolved(true);
        SharedPreferences settings = this.getSharedPreferences("MindBlaster", 0);
        int score = settings.getInt("highscore",0);
        SharedPreferences.Editor e = settings.edit();
        e.putInt("highscore",score+current_puzzle.getComplexity());
        e.commit();
        System.out.println("UPLOAD");
        new DataProcess(db).updatePuzzle(current_puzzle);

    }
}
