package com.geekbrains.notes;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class NoteInformationFragment extends Fragment {
    public static final String ARG_NOTE = "note";
    private Note note;
    TextView noteDateCreationView;
    Calendar dateAndTime = Calendar.getInstance();

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d = (view, year, monthOfYear, dayOfMonth) -> {
        dateAndTime.set(Calendar.YEAR, year);
        dateAndTime.set(Calendar.MONTH, monthOfYear);
        dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        setInitialDateTime();
    };

    // отображаем диалоговое окно для выбора даты
    public void setDate(View v) {
        new DatePickerDialog(getContext(), d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // установка начальных даты и времени
    private void setInitialDateTime() {
        String currentDate = DateUtils.formatDateTime(getContext(), dateAndTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR);
        noteDateCreationView.setText(currentDate);
        note.setDateCreation(currentDate);
    }

    public static NoteInformationFragment newInstance(Note note) {
        NoteInformationFragment f = new NoteInformationFragment();    // создание

        // Передача параметра
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(ARG_NOTE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Таким способом можно получить головной элемент из макета
        View view = inflater.inflate(R.layout.note_information_fragment, container, false);

        EditText noteNameView = view.findViewById(R.id.noteName);
        noteNameView.setText(note.getName());

        EditText noteCategoryView = view.findViewById(R.id.noteCategory);
        noteCategoryView.setText(note.getCategory());

        noteDateCreationView = view.findViewById(R.id.noteDateCreation);
        noteDateCreationView.setText(note.getDateCreation());
        noteDateCreationView.setOnClickListener(this::setDate);

        EditText noteDescriptionView = view.findViewById(R.id.noteDescription);
        noteDescriptionView.setText(note.getDescription());
        return view;
    }
}
