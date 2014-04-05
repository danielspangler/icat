package edu.odu.icat.analytics;

import edu.odu.icat.model.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class AnalyticsEngineTest {

    @Test
    public void TestProminenceAlgorithm() {
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
    }
}
