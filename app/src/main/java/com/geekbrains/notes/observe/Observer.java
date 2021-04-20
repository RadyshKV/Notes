package com.geekbrains.notes.observe;

import com.geekbrains.notes.data.Note;

public interface Observer {
    void updateNote(Note note);
}
