package com.geekbrains.notes;

import android.os.Parcel;
import android.os.Parcelable;


public class Note implements Parcelable {
    private String title;
    private String description;
    private String dateCreation;


    public Note(String title, String description, String dateCreation) {
        this.title = title;
        this.description = description;
        this.dateCreation = dateCreation;
    }

    public Note setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
        return this;
    }

    protected Note(Parcel in) {
        title = in.readString();
        description = in.readString();
        dateCreation = in.readString();
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
        dest.writeString(dateCreation);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDateCreation() {
        return dateCreation;
    }
}
