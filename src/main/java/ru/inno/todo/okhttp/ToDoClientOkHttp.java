package ru.inno.todo.okhttp;

import okhttp3.OkHttpClient;
import ru.inno.todo.ToDoClient;
import ru.inno.todo.model.ToDoItem;

import java.io.IOException;
import java.util.List;

public class ToDoClientOkHttp implements ToDoClient {
    private final String URL;
    private final OkHttpClient client;

    public ToDoClientOkHttp(String url) {
        URL = url;
        client = new OkHttpClient();
    }


    @Override
    public List<ToDoItem> getAll() throws IOException {
        return null;
    }

    @Override
    public ToDoItem getById(int id) {
        return null;
    }

    @Override
    public ToDoItem create(String title) throws IOException {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public ToDoItem renameById(int id, String newName) {
        return null;
    }

    @Override
    public ToDoItem markCompleted(int id, boolean completed) {
        return null;
    }

    @Override
    public void deleteAll() {

    }
}
