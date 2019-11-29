package com.mskrzynski.tankmuseum;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.math.BigInteger;

public interface TankRepository extends MongoRepository<Tank, BigInteger>{
}