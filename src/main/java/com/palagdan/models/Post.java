package com.palagdan.models;

//import com.fasterxml.jackson.annotation.JsonFormat;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "post1")
public class Post extends PanacheEntity {

    public String title;

    public String description;

    public LocalDateTime date = LocalDateTime.now();

    @ManyToOne()
    @JoinColumn(name = "username")
    public User user;


}
