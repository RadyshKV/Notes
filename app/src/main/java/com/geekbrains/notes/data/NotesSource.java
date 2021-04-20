package com.geekbrains.notes.data;

import com.geekbrains.notes.data.Note;

public interface NotesSource {
    Note getNote (int position);
    int size();
    void deleteNote(int position);
    void updateNote (int position, Note note);
    void addNote(Note note);
    void clearNotes();
}
