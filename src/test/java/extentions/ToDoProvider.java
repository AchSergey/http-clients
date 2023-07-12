package extentions;

import org.junit.jupiter.api.extension.*;
import ru.inno.todo.ToDoClient;
import ru.inno.todo.apache.ToDoClientApache;
import ru.inno.todo.model.ToDoItem;

import java.io.IOException;

public class ToDoProvider implements ParameterResolver, AfterEachCallback {
    private final ToDoClient client = new ToDoClientApache("https://todo-app-sky.herokuapp.com");

    private int id;

    // генерить ли значение параметра?
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(ToDoItem.class);
    }

    // генерит значение
    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        try {
            ToDoItem item = client.create("New task to delete");
            id = item.getId();
            return item;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        client.deleteById(id);
    }
}
