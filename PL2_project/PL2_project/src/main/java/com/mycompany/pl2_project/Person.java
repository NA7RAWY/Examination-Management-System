package com.mycompany.pl2_project;

public abstract class Person{
    private String name;
    private String username;
    private String password;
    private int id;
    public Person(String username,String password,String name){
        this.username = username;
        this.name = name;
        this.password = password;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return username;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
       return name;
    }
    
}
       
