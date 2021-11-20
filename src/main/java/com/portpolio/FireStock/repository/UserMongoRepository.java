package com.portpolio.FireStock.repository;

import com.portpolio.FireStock.vo.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserMongoRepository extends MongoRepository<User, String> {
    Optional<User> findBy_id(String _id);
}
