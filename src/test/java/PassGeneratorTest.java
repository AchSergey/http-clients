import org.junit.jupiter.api.Test;
import ru.inno.todo.PasswordGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PassGeneratorTest {

    @Test
    public void test1() {
        PasswordGenerator generator = new PasswordGenerator();
        String pass = generator
                .addLetter('A')
                .addDigit(1)
                .addSpec('#')
                .addSpec('!')
                .addSpec('%')
                .addLetter('B')
                .addDigit(19)
                .getPass();

        assertEquals("A1#!!B19", pass);
    }
}
