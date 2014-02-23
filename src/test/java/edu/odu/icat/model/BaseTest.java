package edu.odu.icat.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class BaseTest {

    @Test
    public void testEqualsMethod() {

        TestClass a = new TestClass();
        TestClass b = new TestClass();
        assertNotEquals(a, b);
        assertEquals(a, a);
    }

    private class TestClass extends Base {
    }

}
