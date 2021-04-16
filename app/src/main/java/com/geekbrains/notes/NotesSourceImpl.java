package com.geekbrains.notes;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

public class NotesSourceImpl implements NotesSource {
    private List<Note> noteSource;
    private Resources resources;    // ресурсы приложения

    public NotesSourceImpl(Resources resources) {
        noteSource = new ArrayList<>();
        this.resources = resources;
    }

    public NotesSourceImpl init(){
        // строки заголовков из ресурсов
        String[] titles = resources.getStringArray(R.array.titles);
        // строки описаний из ресурсов
        String[] descriptions = resources.getStringArray(R.array.descriptions);
        // строки дат из ресурсов
        String[] dates = resources.getStringArray(R.array.dates);
        // заполнение источника данных
        for (int i = 0; i < titles.length; i++) {
            noteSource.add(new Note(titles[i], descriptions[i], dates[i]));
        }
        return this;
    }

    @Override
    public Note getNote(int position) {
        return noteSource.get(position);
    }


    @Override
    public int size() {
        return noteSource.size();
    }
}
