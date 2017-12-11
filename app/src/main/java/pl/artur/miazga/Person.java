package pl.artur.miazga;

import android.text.Editable;

public class Person {

    private String name;
    private String number;

    Person(Editable name, Editable number) {
        this.name = String.valueOf(name);
        this.number = String.valueOf(number);
    }

    String getName() {
        return name;
    }

    String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return name + " (" + number + ") Kupuje prezent dla: ";
    }
}
