package com.geekbrains.notes.data;

import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.util.Map;

public class NoteMapping {
    public static class Fields {
        public final static String DATE = "date";
        public final static String TITLE = "title";
        public final static String DESCRIPTION = "description";
    }

    public static Note toNote(String id, Map<String, Object> doc) {
        Timestamp timeStamp = (Timestamp) doc.get(Fields.DATE);
        Note answer = new Note(
                (String) doc.get(Fields.TITLE),
                (String) doc.get(Fields.DESCRIPTION),
                timeStamp.toDate());
        answer.setId(id);
        return answer;
    }

    public static Map<String, Object> toDocument(Note note) {
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.TITLE, note.getTitle());
        answer.put(Fields.DESCRIPTION, note.getDescription());
        answer.put(Fields.DATE, note.getDateCreation());
        return answer;
    }


}
