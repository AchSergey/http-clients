package extentions;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import ru.inno.todo.ToDoClient;
import ru.inno.todo.apache.ToDoClientApache;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace;

public class ToDoClientProvider implements ParameterResolver {
    public static final String KEY = "ToDoClient";
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(ToDoClient.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        ToDoClient client = new ToDoClientApache("https://todo-app-sky.herokuapp.com");
        extensionContext.getStore(Namespace.GLOBAL).put(KEY, client);
        return client;
    }
}
