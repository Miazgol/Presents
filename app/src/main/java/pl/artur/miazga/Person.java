package pl.artur.miazga;

import android.text.Editable;

class Person {

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
}
