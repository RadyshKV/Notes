package com.geekbrains.notes.data;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NoteSourceFirebaseImpl implements NotesSource {

    private static final String NOTES_COLLECTION = "notes";
    private static final String TAG = "[NoteSourceFirebaseImpl]";

    // База данных Firestore
    private FirebaseFirestore store = FirebaseFirestore.getInstance();

    // Коллекция документов
    private CollectionReference collection = store.collection(NOTES_COLLECTION);

    // Загружаемый список карточек
    private List<Note> noteSource = new ArrayList<Note>();

    @Override
    public NotesSource init(NotesSourceResponse notesSourceResponse) {
        collection.orderBy(NoteMapping.Fields.DATE, Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    noteSource = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> doc = document.getData();
                        String id = document.getId();
                        Note note = NoteMapping.toNote(id, doc);
                        noteSource.add(note);
                    }
                    Log.d(TAG, "success " + noteSource.size() + " qnt");
                    notesSourceResponse.initialized(NoteSourceFirebaseImpl.this);
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "get failed with ", e);
                    }
                });
        return this;
    }

    @Override
    public Note getNote(int position) {
        return noteSource.get(position);
    }

    @Override
    public int size() {
        if (noteSource == null) {
            return 0;
        }
        return noteSource.size();
    }

    @Override
    public void deleteNote(int position) {
        collection.document(noteSource.get(position).getId()).delete();
        noteSource.remove(position);
    }

    @Override
    public void updateNote(int position, Note note) {
        String id = note.getId();
        collection.document(id).set(NoteMapping.toDocument(note));
    }

    @Override
    public void addNote(Note note) {
        collection.add(NoteMapping.toDocument(note)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                note.setId(documentReference.getId());
            }
        });
    }

    @Override
    public void clearNotes() {
        for (Note note : noteSource){
            collection.document(note.getId()).delete();
        }
        noteSource.clear();
    }
}
