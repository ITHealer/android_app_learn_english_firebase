package com.example.toeicexamapplication.vocabulary;

public class Topic {

    String Name_topic;
    public Topic() {}

    public Topic(String name)
    {
        this.Name_topic = name;
    }

    public String getName_topic() {
        return Name_topic;
    }

    public void setName_topic(String name_topic) {
        Name_topic = name_topic;
    }
}
