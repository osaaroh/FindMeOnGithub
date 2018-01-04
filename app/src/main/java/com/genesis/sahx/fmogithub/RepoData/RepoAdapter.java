package com.genesis.sahx.fmogithub.RepoData;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.genesis.sahx.fmogithub.R;

import java.util.ArrayList;

/**
 * Created by SAHX on 12/28/2017.
 */

public class RepoAdapter extends ArrayAdapter<GithubRepoEvent> {

    private Context Kontext;

    public RepoAdapter(Activity context, ArrayList<GithubRepoEvent> repolist){

        super(context, 0, repolist);
        this.Kontext = context;
    }


    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.blueprint, parent, false);
        }

        //get current repo in a particular position in arraylist
       final GithubRepoEvent currentNumber = getItem(position);

        TextView nameTv = (TextView)convertView.findViewById(R.id.repo_name);
        //Set the text of nameTv to the digit text gotten from that Number object using the getDigit method
        nameTv.setText(currentNumber.getFullName());

        TextView descTv = (TextView)convertView.findViewById(R.id.repo_description);
        //Set the text of wordTv to the word text gotten from that Number object using the getWord method
        descTv.setText(currentNumber.getDescription());

        LinearLayout layout = (LinearLayout)convertView.findViewById(R.id.repoView);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = currentNumber.getRepoURL();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                Kontext.startActivity(i);

            }
        });


        return convertView;
    }
}
