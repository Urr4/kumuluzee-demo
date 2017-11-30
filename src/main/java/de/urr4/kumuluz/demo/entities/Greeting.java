package de.urr4.kumuluz.demo.entities;

public class Greeting {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String greet(){
        return "Hello "+name;
    }
}
