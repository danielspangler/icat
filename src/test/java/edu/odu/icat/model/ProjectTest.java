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

    @Test
    public void ensureEntitiesAssignedToForcesExistInTheProject() {
        Project p = new Project("project name", "project description", "an author");
        Entity e1 = new Entity("e1", "c1");
        Entity e2 = new Entity("e2", "c2");
        Entity e3 = new Entity("e3", "c3");

        p.addEntity(e1);
        p.addEntity(e2);

        Force f1 = new Force(e1, e2, 1);
        Force f2 = new Force(e1, e3, 1);
        Force f3 = new Force(e3, e1, 1);


        try {
            p.addForce(f2);
            fail("The destination entity is not in the project");
        } catch (Exception expected) {}

        assertEquals(0, p.getForces().size());
        try {
            p.addForce(f3);
            fail("The origin entity is not in the project");
        } catch (Exception expected) {}
        assertEquals(0, p.getForces().size());

        p.addForce(f1);
        assertEquals(1, p.getForces().size());
        assertSame(f1, p.getForces().get(0));


    }

    @Test
    public void ensureEntitiesWithForcesMayNotBeRemoved() {
        Project p = new Project("project name", "project description", "an author");
        Entity e1 = new Entity("e1", "c1");
        Entity e2 = new Entity("e2", "c2");

        p.addEntity(e1);
        p.addEntity(e2);

        Force f1 = new Force(e1, e2, 1);
        Force f2 = new Force(e2, e1, 1);

        p.addForce(f1);
        p.addForce(f2);

        try {
            p.remoteEntity(e1);
            fail("Entities can't be removed if they have active forces");
        } catch (Exception expected) {}

        p.removeForce(f1);
        p.removeForce(f2);

        assertEquals(2, p.getEntities().size());
        p.remoteEntity(e1);
        assertEquals(1, p.getEntities().size());
        assertFalse(p.getEntities().contains(e1));
    }

}
