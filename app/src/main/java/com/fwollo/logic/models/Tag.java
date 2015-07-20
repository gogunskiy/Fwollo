package com.fwollo.logic.models;

import java.util.List;

public class Tag {

    private String id;
    private String name;
    private User author;
    private List <User> followers;
    private List <TagContent> contents;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public List<TagContent> getContents() {
        return contents;
    }

    public void setContents(List<TagContent> contents) {
        this.contents = contents;
    }

    public boolean isOwner(User user) {
        return user.getId().equalsIgnoreCase(author.getId());
    }
}
