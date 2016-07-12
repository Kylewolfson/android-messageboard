package com.epicodus.discussion.discussionapp;

import org.parceler.Parcel;

/**
 * Created by Guest on 7/12/16.
 */

@Parcel
public class Message {
    String name;

    public Message() {}


    public Message(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}