package com.palagdan;


import com.palagdan.models.User;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.rest.data.panache.ResourceProperties;

@ResourceProperties(hal = true,path="user")
public interface UserResourceRepository extends PanacheEntityResource<User, Long> {

}
