package com.genesis.sahx.fmogithub;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.genesis.sahx.fmogithub.HistoryData.History;
import com.genesis.sahx.fmogithub.HistoryData.HistoryAdapter;
import com.genesis.sahx.fmogithub.HistoryData.HistoryContract;
import com.genesis.sahx.fmogithub.HistoryData.HistoryDBHelper;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    ArrayList<History> fmoUserHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        displayDatabaseInfo();
    }

    public void displayDatabaseInfo(){

        /**To access our database, we instantiate our subclass of SQLiteOpenHelper
          and pass the context, which is the current activity.*/
        HistoryDBHelper dbHelper = new HistoryDBHelper(this);
        //Create a database to open and read from it
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        /**Peform this raw SQL query on the username database to get a cursor
        with all rows from the resentusersearch database SELECT * FROM usernames*/
        Cursor cursor = db.rawQuery("SELECT * FROM "+ HistoryContract.UsernameHistEntry.TABLE_NAME,null);
        //TextView displayView = (TextView) findViewById(R.id.displayHistoryTextView);
        try {
            fmoUserHistory = new ArrayList<History>();
//            displayView.setText("You have searched for " + cursor.getCount() + " github usernames so far.\n\n");
//            displayView.append(HistoryContract.UsernameHistEntry._ID + " - " + HistoryContract.UsernameHistEntry.COLUMN_GITHUB_USERNAME + " - "  + HistoryContract.UsernameHistEntry.COLUMN_GITHUB_URL + " - " + HistoryContract.UsernameHistEntry.COLUMN_GITHUB_REPOS);
            int idColumnIndex = cursor.getColumnIndex(HistoryContract.UsernameHistEntry._ID);
            int gitUserIndex = cursor.getColumnIndex(HistoryContract.UsernameHistEntry.COLUMN_GITHUB_USERNAME);
            int urlIndex = cursor.getColumnIndex(HistoryContract.UsernameHistEntry.COLUMN_GITHUB_URL);
            int repoIndex = cursor.getColumnIndex(HistoryContract.UsernameHistEntry.COLUMN_GITHUB_REPOS);
            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()){
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
//                displayView.append("\n" + cursor.getInt(idColumnIndex) +" - "+ cursor.getString(gitUserIndex) +" - "+ cursor.getString(urlIndex) +" - "+ cursor.getInt(repoIndex));
                fmoUserHistory.add(new History(cursor.getString(gitUserIndex),  cursor.getString(urlIndex), cursor.getInt(repoIndex) ));
            }
        }
        finally {
            cursor.close();

        }
        HistoryAdapter adapter = new HistoryAdapter(HistoryActivity.this, fmoUserHistory);
        ListView listView = (ListView)findViewById(R.id.history_list_view);
        listView.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_clear) {
            HistoryDBHelper dbHelper = new HistoryDBHelper(HistoryActivity.this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(HistoryContract.UsernameHistEntry.TABLE_NAME, null, null);
            displayDatabaseInfo();
            Toast.makeText(HistoryActivity.this,"Success",Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);
    }

}
