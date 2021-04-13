package com.geekbrains.notes;

import android.os.Parcel;
import android.os.Parcelable;


public class Note implements Parcelable {
    private String name;
    private String description;
    private String category;
    private String dateCreation;


    public Note(String name, String dateCreation) {
        this.name = name;
        this.description = "Описание " + name;
        this.dateCreation = dateCreation;
    }

    public Note setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
        return this;
    }

    protected Note(Parcel in) {
        name = in.readString();
        description = in.readString();
        category = in.readString();
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
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(category);
        dest.writeString(dateCreation);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getDateCreation() {
        return dateCreation;
    }
}
