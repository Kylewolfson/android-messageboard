package com.epicodus.discussion.discussionapp;

import org.parceler.Parcel;

/**
 * Created by Guest on 7/11/16.
 */

@Parcel
public class Category {
    String name;

    public Category() {}


    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}