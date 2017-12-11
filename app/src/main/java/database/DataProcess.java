package database;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataProcess {
       private PuzzleDatabase db;
        public DataProcess(PuzzleDatabase database){
            this.db = database;
        }

        @SuppressLint("StaticFieldLeak")
        public void fillDatabase(String JSON){
            List<Puzzle> game_data = new ArrayList<Puzzle>();
            System.out.println(JSON);

            try {
                JSONArray puzzles_json = new JSONArray(JSON);
                for(int x=0; x< puzzles_json.length();x++){
                 JSONObject game_json = puzzles_json.getJSONObject(x);
                 Puzzle game = new Puzzle();
                 System.out.println(game_json.getString("name"));
                 game.setPuzzle_name(game_json.getString("name"));
                 game.setQuestion_text(game_json.getString("question"));
                 game.setImage_loc(game_json.getString("image"));
                 game.setAnswer(game_json.getString("answer"));
                 game.setSolved(false);
                 game.setSolution(game_json.getString("solution"));
                 game.setComplexity(game_json.getInt("complexity"));
                 game_data.add(game);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new AsyncTask<List<Puzzle>, Void,String >() {
                @Override
                protected String doInBackground(List<Puzzle>[] lists) {
                    db.PuzzleDAO().insertMultipleListRecord(lists[0]);
                    return "COMPLETE";
                }
            }.execute(game_data);

        }
    @SuppressLint("StaticFieldLeak")
    public void updatePuzzle(Puzzle p){
        new AsyncTask<Puzzle,Void,String>() {
            @Override
            protected String doInBackground(Puzzle... puzzles) {
                db.PuzzleDAO().updateRecord(puzzles[0]);
                return "Upload complete";
            }
        }.execute(p);

    }


        public PuzzleDatabase getDB(){
            return db;
        }
}
