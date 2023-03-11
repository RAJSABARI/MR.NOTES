package com.rajsabari.notes;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.TextSnapshot;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.zip.DataFormatException;

import io.realm.Realm;
import io.realm.RealmResults;

import java.util.UUID;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    TextView titleoutput;
    TextView descriptionoutput;
    TextView timeoutput;
    TextView uuid; //uuid
    Context context;

    public MyAdapter(Context context, RealmResults<Notes> noteList) {
        this.context = context;
        this.noteList = noteList;
    }

    RealmResults<Notes> noteList;

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notes notes = noteList.get(position);
        holder.titleoutput.setText(notes.getTitle());
        holder.descriptionoutput.setText(notes.getDescription());
        String formateTime = DateFormat.getDateTimeInstance().format(notes.createdTime);
        holder.timeoutput.setText(formateTime);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu menu = new PopupMenu(context, view);
                menu.getMenu().add("DELETE");
                menu.setOnMenuItemClickListener(item -> {
                    if (item.getTitle().equals("DELETE")) {
                        Realm realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        notes.deleteFromRealm();
                        realm.commitTransaction();
                        Toast.makeText(context, "Note Deleted", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                });
                menu.show();
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddNoteActivity.class);
                intent.putExtra("id", notes.uuid);//uuid
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleoutput;
        TextView descriptionoutput;
        TextView timeoutput;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleoutput = itemView.findViewById(R.id.titleoutput);
            descriptionoutput = itemView.findViewById(R.id.descriptionoutput);
            timeoutput = itemView.findViewById(R.id.timeoutput);
        }
    }
}

