package com.example.user.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;


import java.util.ArrayList;

public class ImageList extends AppCompatActivity {
    private RecyclerView recyclerView;
    ArrayList<ImageItem> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);



    }
}
