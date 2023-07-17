package extentions;

import org.junit.jupiter.api.extension.*;
import ru.inno.todo.ToDoClient;
import ru.inno.todo.model.ToDoItem;

import java.io.IOException;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace;

public class ToDoProvider implements ParameterResolver, AfterEachCallback {
    private ToDoClient client;

    private int id;

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(ToDoItem.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        client = (ToDoClient) extensionContext.getStore(Namespace.GLOBAL).get(ToDoClientProvider.KEY);
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
