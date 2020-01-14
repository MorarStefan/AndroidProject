package com.example.traintickets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ListingActivity extends AppCompatActivity {

    private List<String> imageList = new ArrayList<>();
    private List<String> fromTownList = new ArrayList<>();
    private List<String> toTownList = new ArrayList<>();
    private List<String> intervalList = new ArrayList<>();
    private List<String> durationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);

        initialiseItems();
        initRecyclerView();
    }

    private void initialiseItems() {
        fromTownList.add("Brasov");
        toTownList.add("Cluj-Napoca");
        intervalList.add("07:20 - 14:30");

        fromTownList.add("Brasov");
        toTownList.add("Cluj-Napoca");
        intervalList.add("08:55 - 15:23");

        fromTownList.add("Brasov");
        toTownList.add("Cluj-Napoca");
        intervalList.add("15:45 - 22:10");

        fromTownList.add("Brasov");
        toTownList.add("Cluj-Napoca");
        intervalList.add("19:07 - 02:18");

        fromTownList.add("Brasov");
        toTownList.add("Cluj-Napoca");
        intervalList.add("21:55 - 04:34");

        fromTownList.add("Brasov");
        toTownList.add("Cluj-Napoca");
        intervalList.add("23:50 - 07:25");
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this, fromTownList, toTownList, intervalList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
