package com.mskrzynski.tankmuseum;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataMongoTest
@ExtendWith(SpringExtension.class)
class MongoDBTankMuseumTest {
    @Test
    void test(@Autowired TankRepository tankRepository) throws InterruptedException{
        Tank tank1 = new Tank("KW-1", "47.41");
        tankRepository.save(tank1);

        Tank tank2 = new Tank("Panzerkampfwagen VI Tiger", "55.54");
        tankRepository.save(tank2);

        Tank tank3 = new Tank("Panzerkampfwagen VIII Maus", "188.98");
        tankRepository.save(tank3);

        Tank tank4 = new Tank("T-34", "27.94");
        tankRepository.save(tank4);

        Tank tank5 = new Tank("IS-3", "48.68");
        tankRepository.save(tank5);

        Tank tank6 = new Tank("Mk IV Churchill", "38.45");
        tankRepository.save(tank6);

        Tank tank7 = new Tank("Panzerkampfwagen V Panther", "44.45");
        tankRepository.save(tank7);

        Tank tank8 = new Tank("T-34-85 Rudy", "32.48");
        tankRepository.save(tank8);

        try {
            Tank tank9 = new Tank("T-34-85 Rudy", "32.48");
            tankRepository.save(tank9);
        }
        catch(DuplicateKeyException e) {
            System.out.println(e.toString());
        }

        Thread.sleep(5000);
    }
}

