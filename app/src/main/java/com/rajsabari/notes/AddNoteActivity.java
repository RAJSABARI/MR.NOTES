package com.rajsabari.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

import io.realm.Realm;

public class AddNoteActivity extends AppCompatActivity {
    Realm realm;
    String uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        EditText titleinput = findViewById(R.id.title);
        EditText descriptioninput = findViewById(R.id.des);
        TextView savebtn = findViewById(R.id.save1);
        realm = Realm.getDefaultInstance();
        uuid = UUID.randomUUID().toString();
        Intent intent = getIntent();
        if(intent.getExtras() != null) {
            String key = intent.getExtras().getString("id");
            Notes data = getValueFromDatabase(key);
            titleinput.setText(data.title);
            descriptioninput.setText(data.description);
            uuid = data.uuid;
        }

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleinput.getText().toString();
                String description = descriptioninput.getText().toString();
                long createTime = System.currentTimeMillis();
                if (!title.equals("") && !description.equals("")) {
                    Notes note = new Notes();
                    note.setTitle(title);
                    note.setDescription(description);
                    note.setCreatedTime(createTime);
                    note.setUuid(uuid);//uuid

                    realm.executeTransaction(realm -> realm.copyToRealmOrUpdate(note));

                    Toast.makeText(AddNoteActivity.this, "Note Saved", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddNoteActivity.this, "Enter the Field", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public Notes getValueFromDatabase(String id) {
       return realm.where(Notes.class).equalTo("uuid",id).findFirst();
    }
}