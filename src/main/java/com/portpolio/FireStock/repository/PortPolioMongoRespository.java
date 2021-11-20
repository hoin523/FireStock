package com.portpolio.FireStock.repository;


import com.portpolio.FireStock.vo.PortPolio;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PortPolioMongoRespository extends MongoRepository<PortPolio, String> {

    PortPolio findBy_id(String _id);

    Boolean existsBy_id(String _id);

}
