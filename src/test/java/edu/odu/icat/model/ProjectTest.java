package edu.odu.icat.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class ProjectTest {

    @Test
    public void ensureProjectRequiresNameAndAuthor() {
        try {
            new Project("", "whatever", "");
            fail("missing all required parameters");
        } catch (Exception expected) {}

        try {
            new Project("name", "", "");
            fail("missing author");
        } catch (Exception expected) {}

        try {
            new Project("name", "description", "");
            fail("missing author");
        } catch (Exception expected) {}

        final Project p = new Project("n1", "d1", "a1");
        assertEquals(p.getName(), "n1");
        assertEquals(p.getDescription(), "d1");
        assertEquals(p.getAuthor(), "a1");

        try {
            p.setName(null);
            fail("set name to null");
        } catch (Exception expected) {}

        try {
            p.setAuthor(null);
            fail("set author to null");
        } catch (Exception expected) {}

    }

}
