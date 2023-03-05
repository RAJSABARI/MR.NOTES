package com.rajsabari.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;

public class AddNoteActivity extends AppCompatActivity {
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        EditText titleinput = findViewById(R.id.title);
        EditText descriptioninput = findViewById(R.id.des);
        TextView savebtn = findViewById(R.id.save1);
        realm = Realm.getDefaultInstance();
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleinput.getText().toString();
                String description = descriptioninput.getText().toString();
                long createTime = System.currentTimeMillis();
                if (!title.equals("") && !description.equals("")) {
                    realm.beginTransaction();
                    Notes note = realm.createObject(Notes.class);
                    note.setTitle(title);
                    note.setDescription(description);
                    note.setCreatedTime(createTime);
                    realm.commitTransaction();
                    Toast.makeText(AddNoteActivity.this, "Note Saved", Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    Toast.makeText(AddNoteActivity.this, "Enter the Field", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}