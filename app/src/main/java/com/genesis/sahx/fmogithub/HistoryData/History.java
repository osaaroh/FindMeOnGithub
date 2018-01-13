package com.genesis.sahx.fmogithub.HistoryData;

/**
 * Created by SAHX on 1/9/2018.
 */

public class History {
    public final String mUsername;
    public final String mUrl;
    public final int mRepos;

    public History(String username, String url, int repos){
        mUsername = username;
        mUrl = url;
        mRepos = repos;
    }

    public String getUsername(){
        return mUsername;
    }

    public String getUrl(){
        return mUrl;
    }

    public String getRepos(){
        return ""+mRepos;
    }
}
