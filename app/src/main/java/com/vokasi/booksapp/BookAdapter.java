package com.vokasi.booksapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private Context context;
    private ArrayList<BookData> values;
    private LayoutInflater inflater;
    public BookAdapter(Context context, ArrayList<BookData> values){
        this.context=context;
        this.values=values;
        this.inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.list_item,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookData data=values.get(position);
        holder.titleText.setText(data.bookTitle);
        holder.authorText.setText(data.bookAuthor);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleText;
        TextView authorText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText=itemView.findViewById(R.id.text1);
            authorText=itemView.findViewById(R.id.text2);
        }
    }
}
