package edu.odu.icat.service;

import edu.odu.icat.model.Project;
import edu.odu.icat.model.ProjectSerializer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.FileReader;
import java.io.Writer;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class ProjectDAOTest {

    private ProjectDAO projectDAO;
    private ProjectSerializer serializer;
    private String invalidFilePath = "/blah/blah/blah.12312323.txt";

    @Before
    public void setup() {
        projectDAO = new ProjectDAO();
        serializer = mock(ProjectSerializer.class);
        projectDAO.setSerializer(serializer);
    }

    @Test
    public void getProject() {
        try {
            projectDAO.getProject(invalidFilePath);
            fail("Expected exception because of invalid file path");
        } catch (Exception ignore) {}

        try {
            projectDAO.getProject(System.getProperty("user.home"));
            fail("Expected exception because the file path is a directory");
        } catch (Exception ignore) {}

        // expectations
        Project p = new Project("test", "description", "author");
        when(serializer.deserializeFromXML(any(FileReader.class))).thenReturn(p);

        Project result = projectDAO.getProject(new File("").getAbsolutePath() + "/src/test/resources/project-dao.xml");
        assertSame(p, result);
    }

    @Test
    public void saveProject() {
        Project p = new Project("test", "description", "author");

        try {
            projectDAO.saveProject(System.getProperty("user.home"), p);
            fail("Expected exception because the file path is a directory");
        } catch (Exception ignore) {}

        projectDAO.saveProject(System.getProperty("java.io.tmpdir") + "/icat-projectdao-write-test.xml", p);

        verify(serializer).serializeToXML(same(p), any(Writer.class));
    }


}
