package edu.odu.icat.model;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import java.io.Reader;
import java.io.Writer;


/**
 * Converts a Project into and from XML.
 */
public class ProjectSerializer {

    public void serializeToXML(final Project project, final Writer writer) {
        final XStream x = new XStream(new StaxDriver());
        x.toXML(project, writer);
    }

    public Project deserializeFromXML(final Reader reader) {
        final XStream x = new XStream(new StaxDriver());
        return (Project)x.fromXML(reader);
    }

}
