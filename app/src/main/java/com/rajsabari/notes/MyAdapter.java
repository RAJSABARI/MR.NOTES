package com.rajsabari.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
