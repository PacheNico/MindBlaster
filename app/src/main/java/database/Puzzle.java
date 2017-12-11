package database;
import android.arch.persistence.room.*;
@Entity
public class Puzzle {
    @PrimaryKey(autoGenerate = true)
    private int puzzle_id;
    private String question_text;
    private String image_loc;
    private String solution;
    private String answer;
    private boolean solved;
    private String puzzle_name;
    private int complexity;

    /*public Puzzle(String name, String question, String image, String sol, int difficulty){
        this.puzzle_name= name;
        this.question_text=question;
        this.image_loc= image;
        this.solution=solution;
        this.complexity=complexity;
        this.solved=false;
    }*/

    public int getPuzzle_id(){
        return puzzle_id;
    }
    public void setPuzzle_id(int i){
        this.puzzle_id=i;
    }

    public String getQuestion_text() {
        return question_text;
    }

    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }

    public String getImage_loc() {
        return image_loc;
    }

    public void setImage_loc(String image_loc) {
        this.image_loc = image_loc;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public String getPuzzle_name() {
        return puzzle_name;
    }

    public void setPuzzle_name(String puzzle_name) {
        this.puzzle_name = puzzle_name;
    }

    public int getComplexity() {
        return complexity;
    }

    public void setComplexity(int complexity) {
        this.complexity = complexity;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}

