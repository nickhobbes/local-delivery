package com.localdelivery.local_delivery.repositories;

import com.localdelivery.local_delivery.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
