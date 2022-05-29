package com.palagdan.recourses;
import com.palagdan.UserResourceRepository;
import com.palagdan.models.Post;
import com.palagdan.models.User;
import com.palagdan.models.UserForm;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;


@Path("/api")
@Slf4j

public class UserResource {

    @Inject
    UserResourceRepository userResourceRepository;

    // for a single user
    @Inject
    Template section;

    // for a list of users
    @Inject
    Template variable;

    // error
    @Inject
    Template error;


    @Inject
    Template forms;


    @Inject
    Template updateUser;




    @POST
    @Transactional
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/user/new")
    public Object createUser(@MultipartForm UserForm userForm){


        if (!userForm.isValid())
            return error.data("error", "You need to fill all the blank");

        if(!userForm.isUnique())
            return error.data("error", "Try another username, telephone number or mail.");


        User user = userForm.convertIntoUser();
        user.persist();

        return Response.status(Response.Status.SEE_OTHER)
                .location(URI.create("/api/user"))
                .build();
    }

    @GET
    @Path("/user/new")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get_form_user_create(){
        return forms.data("name", "sdlfsd");
    }

    @GET
    @Path("/user")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getAll(){
        return variable.data("_var", User.listAll());
    }

    @GET
    @Path("/user/{id}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getById(@PathParam("id") Long id) {
        User user = userResourceRepository.get(id);

        if(user == null)
            return error.data("error", "User with id: " + id + " not found!");

        return section.data("_var", user);
    }

    @POST
    @Transactional
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/user/{id}/edit")
    public Object update (@PathParam("id") Long id, @MultipartForm UserForm userForm){

        if (!userForm.isValid())
            return error.data("error", "You need to fill all the blank");

        if(!userForm.isUnique())
            return error.data("error", "Try another username, telephone number or mail.");

        User user_found = User.findById(id);

        if(user_found == null)
            return error.data("error", "User with" + id + " not found!");

        user_found = userForm.updateUser(user_found);

        return Response.status(301)
                .location(URI.create("/api/user"))
                .build();
    }

    @GET
    @Path("/user/{id}/edit")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get_form_user_update(@PathParam("id") Long id){
        User user = User.findById(id);
        return updateUser.data("user", user);
    }




    @POST
    @Transactional
    @Path("/user/{id}/delete")
    public Response delete(@PathParam("id") Long id){

        User user = User.findById(id);

        if (user.posts != null) {
            for (Post post : user.posts)
                Post.delete("id", post.id);
        }


        User.delete("id",id);


        return Response.status(301)
                .location(URI.create("/api/user"))
                .build();
    }

    @GET
    @Produces("application/json")
    @Path("/user/{username}")
    public TemplateInstance getByUsername(@PathParam("username") String username){
        User user = User.find("username", username).firstResult();

        if(user == null)
            return error.data("error", "User with username: " + username + " not found!");

        return section.data("_var", user);
    }

    @GET
    @Produces("application/json")
    @Path("/user/name/{name}")
    public TemplateInstance getByName(@PathParam("name") String name){

        List<User> users = User.list("SELECT m FROM User1 m WHERE m.name = ?1", name);

        if(users.isEmpty())
            return error.data("error", "Users with name: " + name + " not found!");

        return variable.data("_var", users);
    }

    @GET
    @Produces("application/json")
    @Path("/user/surname/{surname}")
    public TemplateInstance getBySurname(@PathParam("surname") String surname){

        List<User> users = User.list("SELECT m FROM User1 m WHERE m.surname = ?1", surname);

        if(users.isEmpty())
            return error.data("error", "Users with surname: " + surname + " not found!");

        return variable.data("_var", users);
    }

    @GET
    @Produces("application/json")
    @Path("/user/telephone_number/{telephone_number}")
    public TemplateInstance getByTelephoneNumber(@PathParam("telephone_number") String telephone_number){

        User user = User.find("telephone_number", telephone_number).firstResult();

        if(user == null)
            return error.data("error", "User with telephone number: " + telephone_number + " not found!");

        return section.data("_var", user);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/user/mail/{mail}")
    public TemplateInstance getByMail(@PathParam("mail") String mail){
        User user = User.find("mail", mail).firstResult();

        if(user == null)
            return error.data("error", "User with mail: " + mail + " not found!");


        return section.data("_var", user);
    }




}