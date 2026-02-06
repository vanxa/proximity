package com.vanxacloud.appstudio.proximity;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Person {
    private static int personCounter = 0;
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name;
    private final SimpleBooleanProperty isEmployed;

    public Person(String name, boolean isEmployed) {
        this.id = new SimpleIntegerProperty(++personCounter);
        this.name = new SimpleStringProperty(name);
        this.isEmployed = new SimpleBooleanProperty(isEmployed);
    }


    // getters, setters


    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public boolean isIsEmployed() {
        return isEmployed.get();
    }

    public SimpleBooleanProperty isEmployedProperty() {
        return isEmployed;
    }

    public void setIsEmployed(boolean isEmployed) {
        this.isEmployed.set(isEmployed);
    }
}
