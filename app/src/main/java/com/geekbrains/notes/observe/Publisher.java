package com.geekbrains.notes.observe;

import com.geekbrains.notes.data.Note;

import java.util.ArrayList;
import java.util.List;


public class Publisher {
    private List<Observer> observers;
    public Publisher() {
        observers = new ArrayList<>();
    }

    // Подписать
    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    // Отписать
    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    // Разослать событие
    public void notifySingle(Note note) {
        for (Observer observer : observers) {
            observer.updateNote(note);
            unsubscribe(observer);
        }
    }
}
