package edu.odu.icat.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class BaseTest {

    @Test
    public void testEqualsMethod() {

        TestClass a = new TestClass(); //First Test Case
        TestClass b = new TestClass(); // Second Test Case
        assertNotEquals(a, b);
        assertEquals(a, a);
    }

    private class TestClass extends Base {
    }

}
