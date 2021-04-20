package com.geekbrains.notes.data;

import android.content.res.Resources;

import com.geekbrains.notes.R;

import java.util.ArrayList;
import java.util.Calendar;
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
        // заполнение источника данных
        for (int i = 0; i < titles.length; i++) {
            noteSource.add(new Note(titles[i], descriptions[i], Calendar.getInstance().getTime()));
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

    @Override
    public void deleteNote(int position) {
        noteSource.remove(position);
    }

    @Override
    public void updateNote(int position, Note note) {
        noteSource.set(position, note);
    }

    @Override
    public void addNote(Note note) {
        noteSource.add(note);
    }

    @Override
    public void clearNotes() {
        noteSource.clear();
    }
}
