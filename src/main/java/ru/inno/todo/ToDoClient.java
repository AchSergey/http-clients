package ru.inno.todo;

import ru.inno.todo.model.ToDoItem;

import java.io.IOException;
import java.util.List;

public interface ToDoClient {

    List<ToDoItem> getAll() throws IOException;

    ToDoItem getById(int id) throws IOException;

    ToDoItem create(String title) throws IOException;

    void deleteById(int id) throws IOException;

    ToDoItem renameById(int id, String newName) throws IOException;

    ToDoItem markCompleted(int id, boolean completed) throws IOException;

    void deleteAll() throws IOException;
}
