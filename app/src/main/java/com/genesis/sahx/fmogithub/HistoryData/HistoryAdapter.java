package com.genesis.sahx.fmogithub.HistoryData;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.genesis.sahx.fmogithub.R;

import java.util.ArrayList;

/**
 * Created by SAHX on 1/9/2018.
 */

public class HistoryAdapter extends ArrayAdapter<History> {
    private Context Kontext;
    public HistoryAdapter(Activity context, ArrayList<History> userhistory){
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for three TextViews, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context,0, userhistory);
    }


    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        if (convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.blueprint_history, parent,false);
        }

        History currentUserHistory = getItem(position);

        TextView usernameTextView = (TextView)convertView.findViewById(R.id.history_username);
        usernameTextView.setText(currentUserHistory.getUsername());

        TextView reposTextView = (TextView)convertView.findViewById(R.id.history_repos);
        reposTextView.setText("Public repos: "+currentUserHistory.getRepos());

//        LinearLayout layout = (LinearLayout)convertView.findViewById(R.id.repoView);
//        layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String url = currentUserHistory.getUsername();
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(url));
//                Kontext.startActivity(i);
//
//            }
//        });

        return convertView;
    }
}
