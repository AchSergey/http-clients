package extentions;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import ru.inno.todo.ToDoClient;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace;

public class ToDoListProvider implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        boolean isAnnotationPresent = parameterContext.getParameter().isAnnotationPresent(ItemList.class);
        return parameterContext.getParameter().getType().equals(List.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        ToDoClient client = (ToDoClient) extensionContext.getStore(Namespace.GLOBAL).get(ToDoClientProvider.KEY);


        return new ArrayList<>();
    }

}
