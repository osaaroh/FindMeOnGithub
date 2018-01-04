package com.genesis.sahx.fmogithub.RepoData;

/**
 * Created by SAHX on 12/23/2017.
 */

public class GithubRepoEvent {

    private  String mfullname;

    private  String mdescription;

    private String mUrl;

    public GithubRepoEvent(String fullname, String description, String url){
        mfullname = fullname;
        mdescription = description;
        mUrl = url;
    }

    public String getFullName(){
        return mfullname;
    }

    public String getDescription(){
        return mdescription;
    }

    public String getRepoURL(){
        return mUrl;
    }
}
