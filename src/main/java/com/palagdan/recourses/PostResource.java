package com.palagdan.recourses;

import com.palagdan.PostResourceRepository;
import com.palagdan.models.Post;
import com.palagdan.models.PostForm;
import com.palagdan.models.User;
import com.palagdan.models.UserForm;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import lombok.AllArgsConstructor;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@AllArgsConstructor
@Path("/api")
public class PostResource {

    @Inject
    PostResourceRepository postResourceRepository;


    @Inject
    Template posts;

    @Inject
    Template post_forms;

    @Inject
    Template error;

    @POST
    @Transactional
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/post/new")
    public Object createPost(@MultipartForm PostForm postForm){

        Post post = postForm.convertIntoPost();

        if (post == null)
            return error.data("error", "User with username: " + postForm.username + "not found!");

        post.persist();

        return Response.status(Response.Status.SEE_OTHER)
                        .location(URI.create("/api/post"))
                                .build();
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/post/new")
    public TemplateInstance get_form(){
        return post_forms.data("-var", "sflsdjf");
    }


    @GET
    @Path("/post")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getAll(){
        return posts.data("_var", Post.listAll());
    }



    @POST
    @Transactional
    @Path("/post/{id}/delete")
    public Response delete(@PathParam("id") Long id){
        Post.delete("id",id);

        return Response.status(301)
                .location(URI.create("/api/post"))
                .build();
    }

    @GET
    @Path("/post/{id}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getById(@PathParam("id") Long id) {
        Post post = Post.findById(id);

        if(post == null)
            return error.data("error", "Post with id: " + id + " not found!");

        return post_forms.data("_var", post);
    }


    @POST
    @Transactional
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/post/{id}/edit")
    public Object update (@PathParam("id") Long id, @MultipartForm PostForm postForm){

        Post post_found = Post.findById(id);

        if(post_found == null)
            return error.data("error", "User with" + id + " not found!");

        post_found = postForm.update(post_found);

        return Response.status(301)
                .location(URI.create("/api/post"))
                .build();
    }

}
