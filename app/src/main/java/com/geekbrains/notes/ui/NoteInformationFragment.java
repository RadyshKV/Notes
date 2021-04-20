package com.geekbrains.notes.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import com.geekbrains.notes.R;
import com.geekbrains.notes.data.Note;

import java.text.SimpleDateFormat;

public class NoteInformationFragment extends Fragment {
    public static final String ARG_NOTE = "note";
    private Note note;
    private TextView noteDateCreationView;
    //Calendar dateAndTime = Calendar.getInstance();
    private TextView noteTitle;
    private TextView noteDescriptionView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(ARG_NOTE);
        }
    }

    @SuppressLint("SimpleDateFormat")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Таким способом можно получить головной элемент из макета
        View view = inflater.inflate(R.layout.note_information_fragment, container, false);

        noteTitle = view.findViewById(R.id.noteTitle);
        noteTitle.setText(note.getTitle());

        noteDateCreationView = view.findViewById(R.id.noteDateCreation);
        noteDateCreationView.setText(new SimpleDateFormat("dd-MM-yy").format(note.getDateCreation()));
        //noteDateCreationView.setOnClickListener(this::setDate);

        noteDescriptionView = view.findViewById(R.id.noteDescription);
        noteDescriptionView.setText(note.getDescription());

        setHasOptionsMenu(true);

        initPopupMenu(view);
        return view;
    }

    private void initPopupMenu(View view) {

        noteTitle.setOnClickListener(v -> {
            Activity activity = requireActivity();
            PopupMenu popupMenu = new PopupMenu(activity, v);
            Menu menu = popupMenu.getMenu();
            activity.getMenuInflater().inflate(R.menu.main_fragment, menu);
            popupMenu.setOnMenuItemClickListener(this::menuItemAction);
            popupMenu.show();
        });
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return menuItemAction(item);
    }

    @SuppressLint("NonConstantResourceId")
    boolean menuItemAction(MenuItem item){
        int id = item.getItemId();
        switch (id) {
            case R.id.action_share:
                Toast.makeText(getContext(), "Share", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_add_a_link:
                Toast.makeText(getContext(), "Link", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }



/*
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
    }*/

    public static NoteInformationFragment newInstance(Note note) {
        NoteInformationFragment f = new NoteInformationFragment();    // создание

        // Передача параметра
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        f.setArguments(args);
        return f;
    }
}
