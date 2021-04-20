package com.geekbrains.notes.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.notes.MainActivity;
import com.geekbrains.notes.NoteInformationActivity;
import com.geekbrains.notes.R;
import com.geekbrains.notes.data.Note;
import com.geekbrains.notes.data.NotesSource;
import com.geekbrains.notes.data.NotesSourceImpl;
import com.geekbrains.notes.observe.Observer;
import com.geekbrains.notes.observe.Publisher;

public class NotesFragment extends Fragment {

    public static final String CURRENT_NOTE = "CurrentNote";
    private Note currentNote;
    private boolean isLandscape;
    private NotesSource notesData;
    private NotesAdapter adapter;
    private RecyclerView recyclerView;
    private Navigation navigation;
    private Publisher publisher;
    private boolean moveToLastPosition;

    //Calendar date = Calendar.getInstance();
    //String currentDate = DateUtils.formatDateTime(getContext(), date.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Получим источник данных для списка
        // Поскольку onCreateView запускается каждый раз
        // при возврате в фрагмент, данные надо создавать один раз
        notesData = new NotesSourceImpl(getResources()).init();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notes_fragment, container, false);
        initRecyclerView(view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //initList(view);
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity)context;
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        navigation = null;
        publisher = null;
        super.onDetach();
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "DefaultLocale"})
    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_notes);
        //recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NotesAdapter(notesData, this);
        recyclerView.setAdapter(adapter);

        // Добавим разделитель карточек
        DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(),  LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecoration);

        if (moveToLastPosition){
            recyclerView.smoothScrollToPosition(notesData.size() - 1);
            moveToLastPosition = false;
        }

        // Установим слушателя
        adapter.SetOnItemClickListener((view1, position) -> {
            Toast.makeText(getContext(), String.format("Позиция - %d", position), Toast.LENGTH_SHORT).show();
            currentNote = notesData.getNote(position);
            showNote(currentNote);
        });
    }

    private void showNote(Note currentNote) {
        if (isLandscape) {
            showLandNote(currentNote);
        } else {
            showPortNote(currentNote);
        }
    }

    private void showLandNote(Note currentNote) {
        NoteInformationFragment noteInformationFragment = NoteInformationFragment.newInstance(currentNote);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        //FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.note_info, noteInformationFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    private void showPortNote(Note currentNote) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), NoteInformationActivity.class);
        intent.putExtra(NoteInformationFragment.ARG_NOTE, currentNote);
        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.notes_menu, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchText = (SearchView) search.getActionView();
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getContext(), query, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(getContext(), newText, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_search:
                Toast.makeText(getContext(), "Search", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_sort:
                Toast.makeText(getContext(), "Sort", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_add:
                navigation.addFragment(NoteUpdateFragment.newInstance(), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateNote(Note note) {
                        notesData.addNote(note);
                        adapter.notifyItemInserted(notesData.size() - 1);
                        // это сигнал, чтобы вызванный метод onCreateView
                        // перепрыгнул на конец списка
                        moveToLastPosition = true;
                    }
                });
                return true;
            case R.id.action_clear:
                notesData.clearNotes();
                adapter.notifyDataSetChanged();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = requireActivity().getMenuInflater();
        menuInflater.inflate(R.menu.note_card_menu, menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = adapter.getMenuPosition();
        switch (item.getItemId()) {
            case R.id.action_update:
                navigation.addFragment(NoteUpdateFragment.newInstance(notesData.getNote(position)), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateNote(Note note) {
                        notesData.updateNote(position, note);
                        adapter.notifyItemChanged(position);
                    }
                });
                return true;
            case R.id.action_delete:
                notesData.deleteNote(position);
                adapter.notifyItemRemoved(position);
                return true;
        }
            return super.onContextItemSelected(item);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        // Если это не первое создание, то восстановим текущую позицию
        if (savedInstanceState != null) {
            // Восстановление текущей позиции.
            currentNote = savedInstanceState.getParcelable(CURRENT_NOTE);
        } else {
            // Если воccтановить не удалось, то сделаем объект с первым индексом
            currentNote = notesData.getNote(0); //new Note(getResources().getStringArray(R.array.titles)[0], currentDate);
        }

        if (isLandscape) {
            showLandNote(currentNote);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_NOTE, currentNote);
        super.onSaveInstanceState(outState);
    }


}


