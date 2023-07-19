package ru.inno.todo;

public class PasswordGenerator {

    private String pass = "";

    public PasswordGenerator addDigit(int x) {
        pass += x;
        return this;
    }

    public PasswordGenerator addLetter(char c) {
        pass += c;
        return this;
    }

    public PasswordGenerator addSpec(char s) {
        if (s == '#' || s == '@') {
            pass += s;
        } else {
            pass += "!";
        }
        return this;
    }

    public String getPass() {
        return pass;
    }
}
