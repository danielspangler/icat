package edu.odu.icat.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class EntityTest {

    @Test
    public void ensureEntityNameAndClassificationAreRequired() {
        try {
            new Entity("", "");
            fail("missing all required parameters");
        } catch (Exception expected) {}

        try {
            new Entity("name", "");
            fail("missing classification");
        } catch (Exception expected) {}

        final Entity e = new Entity("n1", "c1");
        assertEquals(e.getName(), "n1");
        assertEquals(e.getClassification(), "c1");

        try {
            e.setName(null);
            fail("set name to null");
        } catch (Exception expected) {}

        try {
            e.setClassification(null);
            fail("set classification to null");
        } catch (Exception expected) {}

    }



}
