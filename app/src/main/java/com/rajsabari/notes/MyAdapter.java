package com.rajsabari.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.zip.DataFormatException;

import io.realm.Realm;
import io.realm.RealmResults;

public class MyAdapter extends  RecyclerView.Adapter<MyAdapter.ViewHolder> {
TextView titleoutput;
TextView descriptionoutput;
TextView timeoutput;


    Context context;

    public MyAdapter(Context context, RealmResults<Notes> noteList) {
        this.context = context;
        this.noteList = noteList;
    }

    RealmResults<Notes> noteList;


    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false)) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notes notes=noteList.get(position);
        holder.titleoutput.setText(notes.getTitle());
        holder.descriptionoutput.setText(notes.getDescription());
       String formateTime= DateFormat.getDateTimeInstance().format(notes.createdTime);
        holder.timeoutput.setText(formateTime);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu menu= new PopupMenu(context,view);
                menu.getMenu().add("DELETE");
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("DELETE")){
                            Realm realm= Realm.getDefaultInstance();
                            realm.beginTransaction();
                            notes.deleteFromRealm();
                            realm.commitTransaction();
                            Toast.makeText(context, "Note Deleted", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
                menu.show();
                return true;
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
            titleoutput=itemView.findViewById(R.id.titleoutput);
            descriptionoutput=itemView.findViewById(R.id.descriptionoutput);
            timeoutput=itemView.findViewById(R.id.timeoutput);
        }
    }
}
