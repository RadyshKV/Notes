package com.geekbrains.notes;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.geekbrains.notes.ui.NoteInformationFragment;

public class NoteInformationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_information);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            finish();
            return;
        }

        if (savedInstanceState == null) {
            NoteInformationFragment noteInformationFragment = new NoteInformationFragment();
            noteInformationFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().replace(R.id.notes_fragment_container, noteInformationFragment).commit();
        }
    }
}
