package com.mygdx.game;

import com.badlogic.gdx.sql.Database;
import com.badlogic.gdx.sql.DatabaseCursor;
import com.badlogic.gdx.sql.DatabaseFactory;
import com.badlogic.gdx.sql.SQLiteGdxException;

/**
 * @author Fernando Giovanini (giovanini.fernando@gmail.com)
 * @since 17/05/15
 */
public class DatabaseConnector
{
    private static final String DATABASE_NAME = "test.db";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_DATABASE_SQL = "CREATE TABLE IF NOT EXISTS `test` (`id` INTEGER NOT NULL UNIQUE, `value` INTEGER NOT NULL DEFAULT 0, PRIMARY KEY(id));";

    private final Database dbHandler;

    public DatabaseConnector() {
        this.dbHandler = DatabaseFactory.getNewDatabase(DATABASE_NAME, DATABASE_VERSION, CREATE_DATABASE_SQL, null);
        this.dbHandler.setupDatabase();
        try {
            this.dbHandler.openOrCreateDatabase();
            this.dbHandler.execSQL(CREATE_DATABASE_SQL);
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
    }

    public void testInsert() {
        try {
            this.dbHandler.execSQL("INSERT OR REPLACE INTO `test` (`id`, `value`) VALUES (1, 2)");
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
    }

    public int testSelect() {
        try {
            DatabaseCursor cursor = this.dbHandler.rawQuery("SELECT `value` FROM `test` WHERE id = 1");
            if (cursor != null && cursor.next()) {
                int value = cursor.getInt(0);

                cursor.close();

                return value;
            }
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
