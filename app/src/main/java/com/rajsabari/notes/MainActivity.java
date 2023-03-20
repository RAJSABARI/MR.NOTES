package com.rajsabari.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class MainActivity extends AppCompatActivity {
    TextView notificationtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notificationtext = findViewById(R.id.notificationtext);
        ImageView addnote = findViewById(R.id.adddtheitem);
        addnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddNoteActivity.class));
            }
        });
        RealmResults<Notes> notesList = Realm.getDefaultInstance().where(Notes.class).findAll().sort("createdTime", Sort.DESCENDING);
        notofication(notesList);
        RecyclerView recyclerView = findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter myAdapter = new MyAdapter(getApplicationContext(), notesList);
        recyclerView.setAdapter(myAdapter);
        notesList.addChangeListener((RealmResults<Notes> notes) -> {
            myAdapter.notifyDataSetChanged();
            notofication(notesList);
        });

    }

    public void notofication(RealmResults<Notes> notesList) {

        if (notesList.size() > 0) {
            notificationtext.setVisibility(View.GONE);
        } else {
            notificationtext.setVisibility(View.VISIBLE);
        }
    }
}