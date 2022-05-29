package com.palagdan;

import com.palagdan.models.Post;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;

public interface PostResourceRepository extends PanacheEntityResource<Post,Long> {
}
