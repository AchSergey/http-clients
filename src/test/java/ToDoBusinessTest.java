import extentions.ToDoClientProvider;
import extentions.ToDoProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.inno.todo.ToDoClient;
import ru.inno.todo.model.ToDoItem;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ToDoClientProvider.class)
public class ToDoBusinessTest {

    @Test
    @DisplayName("Проверяем, что задача создается")
    public void shouldCreateTask(ToDoClient client) throws IOException {
        // получить список задач
        List<ToDoItem> listBefore = client.getAll();
        // создать задачу
        String title = "My super task";
        ToDoItem newItem = client.create(title);

        // проверить, что задача отображается в списке
        assertFalse(newItem.getUrl().isBlank());
        assertFalse(newItem.isCompleted());
        assertTrue(newItem.getId() > 0);
        assertEquals(title, newItem.getTitle());
        // TODO: bug report. Order is null
        assertEquals(0, newItem.getOrder());
        // задач стало на 1 больше
        List<ToDoItem> listAfter = client.getAll();
        assertEquals(1, listAfter.size() - listBefore.size());

        // проверить еще и по id
        ToDoItem single = client.getById(newItem.getId());
        assertEquals(title, single.getTitle());
    }


    @Test
    @ExtendWith(ToDoProvider.class)
    public void shouldRename(ToDoClient client, ToDoItem created) throws IOException {
        // rename
        String myNewTitle = "My new title";
        ToDoItem updated = client.renameById(created.getId(), myNewTitle);

        // get -> assert
        ToDoItem item = client.getById(updated.getId());

        assertEquals(myNewTitle, item.getTitle());
        assertEquals(myNewTitle, updated.getTitle());
        assertEquals(created.getId(), item.getId());
        assertEquals(created.getUrl(), item.getUrl());
        assertEquals(created.getOrder(), item.getOrder());
        assertEquals(created.isCompleted(), item.isCompleted());
    }
}
