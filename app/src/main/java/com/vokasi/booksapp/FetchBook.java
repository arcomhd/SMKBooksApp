package com.vokasi.booksapp;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FetchBook extends AsyncTask<String, Void, String> {
    private ArrayList<BookData> values;
    private BookAdapter bookAdapter;
    private RecyclerView recyclerView;
    private Context context;
    public FetchBook(Context context, ArrayList<BookData> values,
                     BookAdapter bookAdapter, RecyclerView recyclerView){
        this.values=values;
        this.bookAdapter=bookAdapter;
        this.context=context;
        this.recyclerView=recyclerView;
    }

    @Override
    protected String doInBackground(String... strings) {
        String queryString=strings[0];
        String BASE_URL="https://www.googleapis.com/books/v1/volumes?";
        String QUERY_PARAM="q";
        HttpURLConnection urlConnection=null;
        BufferedReader reader=null;
        String bookJSONString=null;
        Uri builUri=Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, queryString).build();
        try {
            URL requestURL=new URL(builUri.toString());
            urlConnection=(HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream=urlConnection.getInputStream();
            StringBuilder builder=new StringBuilder();
            reader=new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line=reader.readLine())!=null){
                builder.append(line+"\n");
            }
            if(builder.length()==0){
                return null;
            }
            bookJSONString=builder.toString();
            Log.d("CLOG",">>"+ bookJSONString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookJSONString;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        values=new ArrayList<>();
        try {
            JSONObject jsonObject=new JSONObject(s);
            JSONArray booksArray=jsonObject.getJSONArray("items");
            String title=null;
            String authors=null;
            int i=0;
            while (i<booksArray.length()){
                JSONObject book=booksArray.getJSONObject(i);
                JSONObject volumeInfo=book.getJSONObject("volumeInfo");
                try{
                    title=volumeInfo.getString("title");
                    if(volumeInfo.has("authors")){
                        authors=volumeInfo.getString("authors");
                    }else {
                        authors="";
                    }
                    BookData bookData=new BookData();
                    bookData.bookTitle=title;
                    bookData.bookAuthor=authors;
                    values.add(bookData);
                }catch (Exception e) {
                    e.printStackTrace();
                }
                i++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        this.bookAdapter=new BookAdapter(context,values);
        this.recyclerView.setAdapter(this.bookAdapter);

    }
}
