package com.geekbrains.notes.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;


public class Note implements Parcelable {
    private String id;
    private String title;
    private String description;
    private Date dateCreation;


    public Note(String title, String description, Date dateCreation) {
        this.title = title;
        this.description = description;
        this.dateCreation = dateCreation;
    }

    protected Note(Parcel in) {
        title = in.readString();
        description = in.readString();
        dateCreation = new Date(in.readLong());
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeLong(dateCreation.getTime());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDateCreation() {
        return dateCreation;
    }
}
