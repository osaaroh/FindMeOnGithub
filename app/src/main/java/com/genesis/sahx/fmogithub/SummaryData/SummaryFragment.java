package com.genesis.sahx.fmogithub.SummaryData;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.genesis.sahx.fmogithub.R;
import com.genesis.sahx.fmogithub.TabbedMainActivity;

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


/**
 * A simple {@link Fragment} subclass.
 */
public class SummaryFragment extends Fragment {

    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = TabbedMainActivity.class.getSimpleName();

    /**Helper method new instance used to create an instance of the Summary Fragment and pass data
     * from an activity to a fragment. To be used in an activity an {@link SummaryFragment} instance
     * is created
    public void newInstance(String b) {
        Bundle args = new Bundle();
        SummaryFragment fragment = new SummaryFragment();
        args.putString("name", b);
        fragment.setArguments(args);
        //fragment.getHostFragmentManager().beginTransaction().commit();
    }*/

   
    // global string that houses api URL
    public String myURL;

    //global string that houses a view RootView
    public View rootview;

    //A bundle to pass data from an activity into this fragment
    public  Bundle bundle;

    public String myUsername;

    //A bitmap image url for profile picture
    public Bitmap imageUrl;

    RoundedBitmapDrawable drawable;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_summary, container, false);
        if(getArguments()!= null) {
                //get the username from the activity via bundle if its not null
                myUsername = getArguments().getString("name");
                Log.i("Summary Fragment", "Will search using Username : " + myUsername);
            }
        else  {
            //fall back and use my username, sucks right?
            myUsername = "master-osaro";
            Log.i("Summary Fragment","NullPointer Ex. Will Connect using default Username from text: "+ myUsername);
        }

        //Instantiate and execute background thread
        myGithubAsyncTask task = new myGithubAsyncTask();
        task.execute();
        return rootview;
    }

    /**
     * Update the screen to display information from the given {@link GithubSummaryEvent}.
     */
    private void updateUi(GithubSummaryEvent user) {
        // Display the name of the user in the UI
        TextView nameTextView = (TextView) rootview.findViewById(R.id.nameTextView);
        nameTextView.setText(user.name);

        // Display the location of the user in the UI
        TextView placeTextView = (TextView) rootview.findViewById(R.id.lacationTextView);
        placeTextView.setText(user.location);

        // Display the number of gists
        TextView githubGistTextView = (TextView) rootview.findViewById(R.id.no_of_gists);
        githubGistTextView.setText(formatToString(user.num_of_gists));

        //Display number of repos
        TextView repoTextView = (TextView) rootview.findViewById(R.id.repos_text_box);
        repoTextView.setText(formatToString(user.num_of_repos));

        // Display the email of the user in the UI
        TextView emailTextView = (TextView) rootview.findViewById(R.id.emailTextView);
        emailTextView.setText(user.email);

        // Display the bio of the user in the UI
        TextView bioTextView = (TextView) rootview.findViewById(R.id.bioTextView);
        bioTextView.setText(user.bio);

        ImageView profileImageView = (ImageView) rootview.findViewById(R.id.user_picture);
        profileImageView.setImageDrawable(user.imageUrl);

    }

    /**The getUsername() method gets the username from the fragment's activity. Username is hardcoded
     for now due to roadblocks*/
    public String getUsername() {

        return myUsername;
    }

    /**Get url method appends the username to the github users api*/
    public final String getUrl() {
        myURL = "https://api.github.com/users/";
        myURL += getUsername();

        Log.i("SummaryFragment","Connecting to URL: "+myURL);
        return myURL;
    }

    /**A helper method to convert integers to strings*/
    public String formatToString(int x){
        return ""+ x;
    }


    /**
     * Returns new URL object from the given string URL.
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
     * Return an {@link GithubSummaryEvent} object by parsing out information about the name, location, public repos and public gists of
     * a github username from the input githubJSON string.
     */
    private GithubSummaryEvent extractFeatureFromJson(String githubJSON) {
        //If the string is null or empty, then return early
        if (TextUtils.isEmpty(githubJSON)) {
            return null;
        }
        try {
            JSONObject baseJsonResponse = new JSONObject(githubJSON);
            String name = baseJsonResponse.getString("name");
            String location = baseJsonResponse.getString("location");
            String email = baseJsonResponse.getString("email");
            String bio = baseJsonResponse.getString("bio");
            String ImageUrl = baseJsonResponse.getString("avatar_url");
            int publicRepos = baseJsonResponse.getInt("public_repos");
            int publicGists = baseJsonResponse.getInt("public_gists");
            try{
                InputStream is = new URL(ImageUrl).openStream();
                /*
                        decodeStream(InputStream is)
                        Decode an input stream into a bitmap.*/
                imageUrl = BitmapFactory.decodeStream(is);
                drawable = RoundedBitmapDrawableFactory.create(getResources(),imageUrl);
                drawable.setCircular(true);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }



            /***N/B Just a keynote for my learning :) ,
             * there is also .getJSONObject(index) if the parent is an array/(objectname) and .getJasonArray getter methods
             * for extracting info from a jason object or array respectively
             * */

            // Create a new {@link Event} object
            return new GithubSummaryEvent(name, location, publicRepos, publicGists, email, bio, drawable);

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the github JSON results", e);
        }
        return null;
    }

    /**An async task to reduce the load on your main activity and run in the background*/
    public class myGithubAsyncTask extends AsyncTask<URL, Void, GithubSummaryEvent> {
        private ProgressDialog dialog = new ProgressDialog(getContext());
        @Override
        protected GithubSummaryEvent doInBackground(URL... urls) {
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
            GithubSummaryEvent user = extractFeatureFromJson(jsonResponse);

            // Return the {@link GithubEvent} object as the result fo the {@link myGithubAsyncTask}
            return user;
        }

        protected void onPreExecute()
        {
            dialog.setMessage("Loading...");
            dialog.show();
        }
        /**
         * Update the screen with the given earthquake (which was the result of the
         * {@link myGithubAsyncTask}).
         */
        @Override
        protected void onPostExecute(GithubSummaryEvent user) {
            if (user == null) {
                return;
            }

            updateUi(user);
            if (dialog.isShowing()){
                dialog.dismiss();}
        }


    }

}
