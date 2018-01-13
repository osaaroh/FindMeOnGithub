package com.genesis.sahx.fmogithub.HistoryData;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.genesis.sahx.fmogithub.HistoryData.HistoryContract.UsernameHistEntry;

/**
 * Created by SAHX on 1/7/2018.
 */

public class HistoryDBHelper extends SQLiteOpenHelper {
    public final static String DATABASE_NAME = "recentusersearch.db";
    public final static int DATABASE_VERSION = 1;

    public HistoryDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_TABLE =  "CREATE TABLE " + UsernameHistEntry.TABLE_NAME + " ("
                + UsernameHistEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UsernameHistEntry.COLUMN_GITHUB_USERNAME + " TEXT NOT NULL, "
                + UsernameHistEntry.COLUMN_GITHUB_URL + " TEXT, "
                + UsernameHistEntry.COLUMN_GITHUB_REPOS + " INTEGER NOT NULL DEFAULT 0);";
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
