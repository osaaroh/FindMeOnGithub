package com.genesis.sahx.fmogithub.HistoryData;

import android.provider.BaseColumns;

/**
 * Created by SAHX on 1/7/2018.
 */

public class HistoryContract {
    public HistoryContract(){}
    public static final class UsernameHistEntry implements BaseColumns{
        public final static String TABLE_NAME = "usernames";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_GITHUB_USERNAME = "username";
        public final static String COLUMN_GITHUB_URL = "url";
        public final static String COLUMN_GITHUB_REPOS = "repos";

    }
}
