package edu.odu.icat.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class ProjectSerializerTest {

    @Test
    public void testSerialization() {

        Project p = new Project("project name", "project description", "an author");
        Entity e1 = new Entity("e1", "c1");
        Entity e2 = new Entity("e2", "c2");
        Entity e3 = new Entity("e3", "c3");
        Entity e4 = new Entity("e4", "c4");
        Entity e5 = new Entity("e5", "c5");

        p.addEntity(e1);
        p.addEntity(e2);
        p.addEntity(e3);
        p.addEntity(e4);
        p.addEntity(e5);

        Force f1 = new Force(e1, e2, 1);
        Force f2 = new Force(e1, e3, 1);
        Force f3 = new Force(e2, e3, 1);
        Force f4 = new Force(e2, e1, 1);
        Force f5 = new Force(e3, e1, 1);

        p.addForce(f1);
        p.addForce(f2);
        p.addForce(f3);
        p.addForce(f4);
        p.addForce(f5);


        ProjectSerializer serializer = new ProjectSerializer();
        StringWriter writer = new StringWriter();
        serializer.serializeToXML(p, writer);

        StringReader reader = new StringReader(writer.toString());
        Project theCopy = serializer.deserializeFromXML(reader);

        assertNotNull(theCopy);
        assertEquals(5, theCopy.getEntities().size());
        assertEquals(5, theCopy.getForces().size());

        assertIdAndNotSame(e1, theCopy.getEntities(), 0);
        assertIdAndNotSame(e2, theCopy.getEntities(), 1);
        assertIdAndNotSame(e3, theCopy.getEntities(), 2);
        assertIdAndNotSame(e4, theCopy.getEntities(), 3);
        assertIdAndNotSame(e5, theCopy.getEntities(), 4);


        for (int i = 0; i < p.getForces().size(); i++) {
            Force origForce = p.getForces().get(i);
            Force copyForce = theCopy.getForces().get(i);
            assertEquals(origForce, copyForce);
            assertNotSame(origForce, copyForce);

            assertEquals(origForce.getOrigin(), copyForce.getOrigin());
            assertNotSame(origForce.getOrigin(), copyForce.getOrigin());

            assertEquals(origForce.getDestination(), copyForce.getDestination());
            assertNotSame(origForce.getDestination(), copyForce.getDestination());

        }
    }

    private void assertIdAndNotSame(Object expected, List<? extends Base> l, int index) {
        assertEquals(expected, l.get(index));
        assertNotSame(expected, l.get(0));
    }





}
