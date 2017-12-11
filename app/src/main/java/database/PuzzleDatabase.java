package database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Puzzle.class}, version = 10, exportSchema = false)
public abstract class PuzzleDatabase extends RoomDatabase {
    public abstract PuzzleDAO PuzzleDAO();
}
