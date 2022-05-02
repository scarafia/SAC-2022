package utn.frc.dlc.tutor.sac.web.api.cdi;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

public class CDITestClass implements Serializable {

    private final Date created;
    private final int value;

    public CDITestClass(int value) {
        this.created = new Date();
        this.value = value;
    }

    public CDITestClass() {
        this(new Random().nextInt(100));
    }

    public Date getCreated() {
        return created;
    }

    public int getValue() {
        return value;
    }

}
