package com.example.newz3;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    DatabaseReference mdatabse;
    private ArrayList<String> mNames = new ArrayList<>();
    private RecyclerViewPager muserslist;
    private View mViewmain;
    int[] mPlaceList;

    private RecyclerView mPeopleRV;
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<News, MainActivity.NewsViewHolder> mPeopleRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         getImages();
        setTitle("News");
        FirebaseApp.initializeApp(MainActivity.this);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("News");
       mDatabase.keepSynced(true);

        mPeopleRV = (RecyclerView) findViewById(R.id.swipeuprecycler);

        DatabaseReference personsRef = FirebaseDatabase.getInstance().getReference().child("News");
        Query personsQuery = personsRef.orderByKey();

        mPeopleRV.hasFixedSize();
        mPeopleRV.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions personsOptions = new FirebaseRecyclerOptions.Builder<News>().setQuery(personsQuery, News.class).build();

        mPeopleRVAdapter = new FirebaseRecyclerAdapter<News, NewsViewHolder>(personsOptions) {
            @Override
            protected void onBindViewHolder(MainActivity.NewsViewHolder holder, final int position, final News model) {
                holder.setTitle(model.getTitle());
                holder.setDesc(model.getDesc());
                holder.setImage(getBaseContext(), model.getImage());

            }

            @Override
            public MainActivity.NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.news_row, parent, false);

                return new MainActivity.NewsViewHolder(view);
            }
        };

        mPeopleRV.setAdapter(mPeopleRVAdapter);


    }
    private void getImages(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        mPlaceList = new int[]{R.drawable.techpush, R.drawable.worldpush, R.drawable.hindipush, R.drawable.politicspush, R.drawable.btownpush, R.drawable.sportspush};
            mNames.add("TECH");
            mNames.add("WORLD");
            mNames.add("HINDI");
            mNames.add("SPORTS");
            mNames.add("POLITICS");
            mNames.add("BTOWN");
        initRecyclerView();

    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames, mPlaceList);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onStart() {
        super.onStart();
        mPeopleRVAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPeopleRVAdapter.stopListening();


    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public NewsViewHolder(View itemView){
            super(itemView);
            mView = itemView;
        }
        public void setTitle(String title){
            TextView post_title = (TextView)mView.findViewById(R.id.post_title);
            post_title.setText(title);
        }
        public void setDesc(String desc){
            TextView post_desc = (TextView)mView.findViewById(R.id.post_desc);
            post_desc.setText(desc);
        }

        public void setImage(Context ctx, String image) {
            ImageView post_image = (ImageView) mView.findViewById(R.id.post_image);
            Picasso.with(ctx).load(image).placeholder(R.drawable.imagereplace).into(post_image);
        }
    }
}



