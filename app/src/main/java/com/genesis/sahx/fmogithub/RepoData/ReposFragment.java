package com.genesis.sahx.fmogithub.RepoData;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.genesis.sahx.fmogithub.R;
import com.genesis.sahx.fmogithub.TabbedMainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

//import com.genesis.sahx.fmogithub.TabbedMainActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReposFragment extends Fragment {

    public String myURL;

    public View rootview;

    public ArrayList<GithubRepoEvent> repoEvents = new ArrayList<GithubRepoEvent>();

    public ListView listView;

    public RepoAdapter adapter;


    /**
     * Tag for the log messages
     */

    public static final String LOG_TAG = TabbedMainActivity.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_repo,container,false);
        //run Async-task
        myGithubAsyncTask task = new myGithubAsyncTask();
        task.execute();
        return rootview;
    }

    /**
     * Update the UI to display information from the given {@link GithubRepoEvent}.
     */

    private void updateUi() {
        //used runOnUiThread to combat an Exception
         getActivity().runOnUiThread(new Runnable() {
             @Override
             public void run() {
                 adapter = new RepoAdapter(getActivity(), repoEvents);
                 listView = (ListView)rootview.findViewById(R.id.repo_list_view);
                 listView.setAdapter(adapter);
             }
         });

    }

    /**The getUsername() method gets the username from the fragment's activity. Username is hardcoded
    for now due to roadblocks*/
    public String getUsername() {
        return "master-osaro";
    }

    /**Get url method appends the username to the github repos api*/
    public final String getUrl() {
        myURL = "https://api.github.com/users/";
        myURL += getUsername();
        myURL += "/repos";

        Log.i("SummaryFragment", "Connecting to URL: " + myURL);
        return myURL;
    }

    /**
     * Returns new URL object from the given string URL. say getUrl();
     */
    private URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return url;
    }

    /**
     * Make an HTTP request to the given myURL and return a String as the response.
     */
    private String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        //if myURL is null, return jason response and no need to go through the logic - sahx
        if (jsonResponse == null) {
            return jsonResponse;
        }
        //---

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);// to set a time limit for how long the wait will be for incoming d
            //dats.
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            //helps if your connection is bad
            //line below establishes a connection to the server
            urlConnection.connect();
            //if the request  was successful (response code = 200)
            //read input stream and parse the response
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("TabbedMainActivity", "makeHttpRequest wrong response code " + urlConnection.getResponseCode());
            }
            //---
        } catch (IOException e) {
            // TODO: Handle the exception
            Log.e("MainActivity", "makeHttpRequest: IOException" + e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
    /**
     * Return an {@link GithubRepoEvent} object by parsing out information about the name, repo description and repo url
     * a github username from the input githubJSON string.
     */
    private ArrayList<GithubRepoEvent> extractFeatureFromJson(String githubJSON) {
        //If the string is null or empty, then return early
        if (TextUtils.isEmpty(githubJSON)) {
            return null;
        }
        try {
            JSONArray baseJsonArray = new JSONArray(githubJSON);
            if (baseJsonArray != null && baseJsonArray.length() > 0) {
                for (int i = 0; i < baseJsonArray.length(); i++) {
                    JSONObject currentObject = baseJsonArray.getJSONObject(i);
                    String name = currentObject.getString("name");
                    String description = currentObject.getString("description");
                    String url = currentObject.getString("html_url");
                    Log.i("SummaryFragment", "Here's the data you need: " +i+" "+ name+" "+ description+ " "+ url);
                    //return new GithubRepoEvent(name, description, url);
                    repoEvents.add(new GithubRepoEvent(name,description,url));
                }
            }
            /***N/B Just a keynote for my learning :) ,
             * there is also .getJSONObject(index) if the parent is an array/(objectname) and .getJasonArray getter methods
             * for extracting info from a jason object or array respectively
             * */
            // Create a new {@link Event} object


        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the github JSON results", e);
        }
        return null;
    }

    /**An async task to reduce the load on your main activity and run in the background*/
    public class myGithubAsyncTask extends AsyncTask<URL, Void, Void> {
        @Override
        protected Void doInBackground(URL... urls) {
            // Create URL object
            URL url = createUrl(getUrl());
            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                // TODO Handle the IOException
            }
            // Extract relevant fields from the JSON response and create an {@link GithubEvent} object
            extractFeatureFromJson(jsonResponse);
            updateUi();
            // Return the {@link GithubEvent} object as the result fo the {@link myGithubAsyncTask}
        return null;
        }
         protected void onPreExecute()
        {
            dialog.setMessage("Loading...");
            dialog.show();
         }
        protected void onPostExecute(Void params) {
            super.onPostExecute(params);
            if (dialog.isShowing()){
                dialog.dismiss();}
        }
        
    }
}
