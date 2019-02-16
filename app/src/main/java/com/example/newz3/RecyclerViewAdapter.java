package com.example.newz3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;


/**
 * Created by User on 2/12/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<Integer> mimages = new ArrayList<>();
    private Context mContext;


    public RecyclerViewAdapter(Context context, ArrayList<String> names, ArrayList<Integer> images) {
        mNames = names;
        mimages = images;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Log.d(TAG, "onBindViewHolder: called.");

        holder.mPlace.setImageResource(mimages.get(position));
        //holder.name.setText(mNames.get(position));

    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView mPlace;
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            mPlace = itemView.findViewById(R.id.image_view);
        }
    }
}
