package com.irdaislakhuafa.springbootjwtintegrations.models.repositories;

import java.util.Optional;

import com.irdaislakhuafa.springbootjwtintegrations.models.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    public Optional<User> findByUsername(String username);
}
