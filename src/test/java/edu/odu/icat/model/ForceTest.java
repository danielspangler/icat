package edu.odu.icat.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class ForceTest {

    @Test
    public void ensureForceMustHaveOriginAndDestination() {
        final Entity o = new Entity("n1", "c1");
        final Entity d = new Entity("n2", "c2");
        try {
            new Force(null, null, 4);
            fail("missing all required parameters");
        } catch (Exception expected) {}

        try {
            new Force(o, null, 3);
            fail("missing destination");
        } catch (Exception expected) {}

        final Force f = new Force(o, d, 8);
        assertSame(f.getOrigin(), o);
        assertSame(f.getDestination(), d);
        assertEquals(f.getWeight(), 8);

        try {
            f.setOrigin(null);
            fail("set origin to null");
        } catch (Exception expected) {}

        try {
            f.setDestination(null);
            fail("set destination to null");
        } catch (Exception expected) {}
    }

    @Test
    public void ensureOriginAndDestinationCannotBeTheSame() {
        final Entity o = new Entity("n1", "c1");
        final Entity d = new Entity("n2", "c2");
        try {
            new Force(o, o, 4);
            fail("origin and destination cannot be the same");
        } catch (Exception expected) {}

        Force f = new Force(o, d, 3);

        try {
            f.setOrigin(d);
            fail("cannot set origin to the destination");
        } catch (Exception expected) {}

        try {
            f.setDestination(o);
            fail("cannot set destination to the origin");
        } catch (Exception expected) {}

    }


}
