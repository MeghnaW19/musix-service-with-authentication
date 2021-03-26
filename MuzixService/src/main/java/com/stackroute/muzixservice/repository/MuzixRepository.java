package com.stackroute.muzixservice.repository;

import com.stackroute.muzixservice.domain.Track;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface MuzixRepository extends MongoRepository<Track,String> {
}
