package com.palagdan.recourses;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("")
public class HomeResource {

    @Inject
    Template based;



    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get_home(){
        return based.data("name", "dan");
    }





}
