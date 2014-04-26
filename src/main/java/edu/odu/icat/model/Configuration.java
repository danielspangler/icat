package edu.odu.icat.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by daniel on 4/26/14.
 */
public class Configuration {

    private Map<String, String> recentProjects = new HashMap<String, String>(); // key==projectName, value==path


    public Map<String, String> getRecentProjects() {
        return recentProjects;
    }

}
