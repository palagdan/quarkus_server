package com.palagdan.models;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.validation.constraints.NotBlank;
import javax.ws.rs.FormParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

public class UserForm {

    public @FormParam("username") @PartType(MediaType.TEXT_PLAIN)  String username;

    public @FormParam("name") @PartType(MediaType.TEXT_PLAIN) String name;

    public @FormParam("surname") @PartType(MediaType.TEXT_PLAIN) String surname;

    public @FormParam("mail") @PartType(MediaType.TEXT_PLAIN) String mail;

    public @FormParam("telephone_number") @PartType(MediaType.TEXT_PLAIN) String telephone_number;



    public User convertIntoUser(){
        User user = new User();
        user.username = username;
        user.name = name;
        user.surname = surname;
        user.mail = mail;
        user.telephone_number = telephone_number;
        return user;
    }


    public User updateUser(User toUpdate){
        toUpdate.username = username;
        toUpdate.name = name;
        toUpdate.surname = surname;
        toUpdate.mail = mail;
        toUpdate.telephone_number = telephone_number;

        return toUpdate;
    }



    public Boolean isValid(){
        return !username.isBlank() && !name.isBlank() && !surname.isBlank()
                && !mail.isBlank() && !telephone_number.isBlank();
    }

    public Boolean isUnique(){
        if(User.find("username", username).firstResult() == null && User.find("mail", mail).firstResult() == null && User.find("telephone_number", telephone_number).firstResult() == null)
            return true;

        return false;
    }
}
