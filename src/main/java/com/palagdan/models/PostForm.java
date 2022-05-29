package com.palagdan.models;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.ws.rs.FormParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

public class PostForm {


    public @FormParam("title") @PartType(MediaType.TEXT_PLAIN) String title;
    public @FormParam("description") @PartType(MediaType.TEXT_PLAIN) String description;
    public @FormParam("username") @PartType(MediaType.TEXT_PLAIN) String username;

    public Post convertIntoPost(){
        Post post = new Post();
        post.title = title;
        post.description = description;

        if (User.find("username", username).firstResult() == null)
            return null;

        post.user = User.find("username", username).firstResult();
        return post;
    }

    public Post update(Post toUpdate){
        toUpdate.title = title;
        toUpdate.description = description;

        return toUpdate;
    }
}
