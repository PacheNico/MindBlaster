package database;

import android.arch.persistence.room.*;

import java.util.List;

@Dao
public interface PuzzleDAO {

    @Insert
    void insertMultipleRecord(Puzzle... puzzles);

    @Insert
    void insertMultipleListRecord(List<Puzzle> puzzles);

    @Insert
    void insertOnlySingleRecord(Puzzle university);

    @Query("SELECT * FROM Puzzle")
    List<Puzzle> fetchAllData();

    @Query("SELECT * FROM Puzzle WHERE solved=0 order by complexity limit 1")
    Puzzle getSingleRecord();

    @Update
    void updateRecord(Puzzle puzzle);

    @Delete
    void deleteRecord(Puzzle puzzle);
}

