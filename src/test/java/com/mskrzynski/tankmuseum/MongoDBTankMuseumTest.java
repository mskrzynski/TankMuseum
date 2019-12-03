package com.mskrzynski.tankmuseum;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Objects;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@DataMongoTest
@ExtendWith(SpringExtension.class)
class MongoDBTankMuseumTest {

    @Autowired TankRepository tankRepository;
    @Autowired MongoOperations mongoOperations;

    @Test
    void createRepositoryTest() {
        assertNotNull(tankRepository);
    }

    @Test
    void createTankTest() {
        Tank tank = new Tank("KW-1", "47.41");
        Tank tankInserted = tankRepository.save(tank);
        assertThat(tankInserted.getTankName(), is(equalTo("KW-1")));
        assertThat(Objects.requireNonNull(mongoOperations.findById(tankInserted.getTankID(), Tank.class)).getTankName(), is(equalTo("KW-1")));
        System.out.println("createTankTest ID: " + tankInserted.getTankID());
    }

    @Test
    void removeTankTest() {
        Tank removeTank = mongoOperations.findOne(query(where("tankName").is("KW-1")), Tank.class);
        assert removeTank != null;
        System.out.println("removeTankTest ID: " + removeTank.getTankID());
        tankRepository.delete(removeTank);
        assertThat(mongoOperations.findById(removeTank.getTankID(), Tank.class), is(equalTo(null)));
    }
}

