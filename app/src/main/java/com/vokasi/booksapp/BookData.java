package com.vokasi.booksapp;

import android.os.Parcel;
import android.os.Parcelable;

public class BookData implements Parcelable {
    public String bookTitle;
    public String bookAuthor;
    public String bookImage;
    public String bookDescription;

    public BookData(){
        
    }

    protected BookData(Parcel in) {
        bookTitle = in.readString();
        bookAuthor = in.readString();
        bookImage = in.readString();
        bookDescription = in.readString();
    }

    public static final Creator<BookData> CREATOR = new Creator<BookData>() {
        @Override
        public BookData createFromParcel(Parcel in) {
            return new BookData(in);
        }

        @Override
        public BookData[] newArray(int size) {
            return new BookData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(bookTitle);
        parcel.writeString(bookAuthor);
        parcel.writeString(bookImage);
        parcel.writeString(bookDescription);
    }
}
