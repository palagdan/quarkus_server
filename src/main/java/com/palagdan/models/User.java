package com.palagdan.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

/**
 * Example JPA entity.
 *
 * To use it, get access to a JPA EntityManager via injection.
 *
 * {@code
 *     @Inject
 *     EntityManager em;
 *
 *     public void doSomething() {
 *         MyEntity entity1 = new MyEntity();
 *         entity1.setField("field-1");
 *         em.persist(entity1);
 *
 *         List<MyEntity> entities = em.createQuery("from MyEntity", MyEntity.class).getResultList();
 *     }
 * }
 */
@Entity(name = "user1")
public class User extends PanacheEntity {

    @NotBlank()
    @Column(name = "username", nullable = false, unique = true)
    public String username;

    @NotBlank()
    @Column(name = "name", nullable = false)
    public String name;

    @NotBlank()
    @Column (name = "surname", nullable = false)
    public String surname;

    @NotBlank()
    @Email
    @Column(name = "mail", nullable = false, unique = true)
    public String mail;

    @NotBlank()
    @Column(name= "telephone_number", nullable = false, unique = true)
    public String telephone_number;

    @OneToMany()
    public Collection<Post> posts;





}
