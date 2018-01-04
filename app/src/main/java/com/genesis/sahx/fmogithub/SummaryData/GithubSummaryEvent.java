package com.genesis.sahx.fmogithub.SummaryData;

//import android.app.usage.UsageEvents.Event;

import android.support.v4.graphics.drawable.RoundedBitmapDrawable;

/**
 * {@Event} represents an earthquake event. It holds the details
 * of that event such as title (which contains magnitude and location
 * of the earthquake), as well as time, and whether or not a tsunami
 * alert was issued during the earthquake.
 *
 * Created by SAHX on 12/4/2017.
 */
public class GithubSummaryEvent {
    //name of the github user
    public final String name;
    //location of the user
    public final String location;
    //number of github repos
    public final int num_of_repos;
    //number of github gists
    public final int num_of_gists;
    //github url
    public final String email;
    //github bio
    public final String bio;

    public final RoundedBitmapDrawable imageUrl;

/**
 * Constructs a new {@link GithubSummaryEvent}
 *
 *@param eventName
 *@param eventLocation
 *@param eventNumofRepos
 *@param eventNumofGists
 *
 * */

public GithubSummaryEvent(String eventName, String eventLocation, int eventNumofRepos, int eventNumofGists, String eventEmail , String eventBio, RoundedBitmapDrawable myImageUrl){
    name = eventName;
    location = eventLocation;
    num_of_repos = eventNumofRepos;
    num_of_gists = eventNumofGists;
    email = eventEmail;
    bio = eventBio;
    imageUrl = myImageUrl;
  }
}
