package org.gradle;

import com.google.gson.Gson;


public class Person {
    private final String name;

    public Person(String name) {
        this.name = name;        
    }

    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
    	return new Gson().toJson(this);
    }
}
