package utn.frc.dlc.tutor.sac.web.api.cdi;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;

@Dependent
public class CDITestProducer {

    @Produces
    @ProducedCDITestClass
    public CDITestClass create() {
        return new CDITestClass(25);
    }

    public void destroy(@Disposes @ProducedCDITestClass CDITestClass test) {
        // todo: free resouces here
    }

}
