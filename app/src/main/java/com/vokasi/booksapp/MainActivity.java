package com.vokasi.booksapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText editSearch;
    private Button buttonSearch;
    private RecyclerView recyclerView;
    private ArrayList<BookData> values;
    private BookAdapter bookAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editSearch=findViewById(R.id.edit_query);
        buttonSearch=findViewById(R.id.button_search);
        recyclerView=findViewById(R.id.recycler_view);
        values=new ArrayList<>();
        bookAdapter=new BookAdapter(this,values);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(bookAdapter);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchBooks();
            }
        });
    }

    private void searchBooks(){
        String queryString=editSearch.getText().toString();
        ConnectivityManager connectivityManager=(ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected()){
            if(queryString.length()>0) {
                new FetchBook(this, values, bookAdapter, recyclerView)
                        .execute(queryString);
            }
        }else {
            Toast.makeText(this, "Tidak terhubung ke internet",
                    Toast.LENGTH_SHORT).show();
        }

    }


}