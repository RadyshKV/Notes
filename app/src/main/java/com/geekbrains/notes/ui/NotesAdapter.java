package com.geekbrains.notes.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.notes.R;
import com.geekbrains.notes.data.Note;
import com.geekbrains.notes.data.NotesSource;

import java.text.SimpleDateFormat;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private final static String TAG = "NotesAdapter";
    private NotesSource noteSource;
    private final Fragment fragment;
    private int menuPosition;

    private Context context;
    private OnItemClickListener itemClickListener;  // Слушатель будет устанавливаться извне

    public NotesAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setNoteSource(NotesSource noteSource){
        this.noteSource = noteSource;
        notifyDataSetChanged();
    }



    public int getMenuPosition() {
        return menuPosition;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder holder, int position) {
        Note note = noteSource.getNote(position);
        holder.setNote(note);
    }

    @Override
    public int getItemCount() {
        return noteSource == null ? 0 : noteSource.size();
    }

    // Сеттер слушателя нажатий
    public void SetOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    // Интерфейс для обработки нажатий, как в ListView
    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView description;
        private final TextView date;

        @SuppressLint("NewApi")
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date);
            registerContextMenu(itemView);

            // Обработчик нажатий на этом ViewHolder
            title.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(v, getAdapterPosition());
                }
            });

            title.setOnLongClickListener(v -> {
                menuPosition = getLayoutPosition();
                itemView.showContextMenu(10,10);
                return true;
            });

        }

        private void registerContextMenu(@NonNull View itemView) {
            if (fragment != null){
                itemView.setOnLongClickListener(v -> {
                    menuPosition = ViewHolder.this.getLayoutPosition();
                    return false;
                });
                fragment.registerForContextMenu(itemView);
            }
        }

        @SuppressLint("SimpleDateFormat")
        public void setNote(Note note){
            title.setText(note.getTitle());
            description.setText(note.getDescription());
            date.setText(new SimpleDateFormat("dd-MM-yy").format(note.getDateCreation()));
        }

    }
}
