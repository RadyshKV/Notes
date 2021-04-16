package com.geekbrains.notes;

public interface NotesSource {
    Note getNote (int position);
    int size();
}
