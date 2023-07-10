package ru.inno.todo;

import ru.inno.todo.model.CreateToDo;
import ru.inno.todo.model.ToDoItem;

import java.io.IOException;
import java.util.List;

public interface ToDoClient {

    List<ToDoItem> getAll() throws IOException;

    ToDoItem getById(int id);

    ToDoItem create(CreateToDo createToDo) throws IOException;

    void deleteById(int id);

    ToDoItem renameById(int id, String newName);

    ToDoItem markCompleted(int id, boolean completed);

    void deleteAll();
}
