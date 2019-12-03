package com.mskrzynski.tankmuseum;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigInteger;

@Document(collection = "tanks", collation = "en")
public class Tank {
    @Id
    @NotEmpty
    private BigInteger tankID; //Cannot use Integer, must use String or BigInteger
    @Indexed(unique=true)
    @NotEmpty
    private String tankName;
    @NotEmpty
    @Min(0)
    private String tankWeight;

    public Tank() {
    }

    public Tank(String tankName, String tankWeight) {
        this.setTankName(tankName);
        this.setTankWeight(tankWeight);
    }

    public BigInteger getTankID() {
        return tankID;
    }

    void setTankID(BigInteger tankID) {
        this.tankID = tankID;
    }

    public String getTankName() {
        return tankName;
    }

    void setTankName(String tankName) {
        this.tankName = tankName;
    }

    public String getTankWeight() {
        return tankWeight;
    }

    void setTankWeight(String tankWeight) {
        this.tankWeight = tankWeight;
    }
}
