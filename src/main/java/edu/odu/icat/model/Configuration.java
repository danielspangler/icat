package edu.odu.icat.model;

import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by daniel on 4/26/14.
 */
public class Configuration {

    private List<ProjectInfo> recentProjects = new ArrayList<ProjectInfo>();

    public List<ProjectInfo> getRecentProjects() {
        return Collections.unmodifiableList(recentProjects);
    }

    public void addRecentProject(ProjectInfo info) {
        recentProjects.remove(info);
        recentProjects.add(0, info);
    }

    public static class ProjectInfo {
        private String name;
        private String path;

        public ProjectInfo(String name, String path) {
            checkArgument(!Strings.isNullOrEmpty(path), "The path may not be empty");
            this.name = name;
            this.path = path;
        }

        public String getName() {
            return name;
        }

        public String getPath() {
            return path;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ProjectInfo that = (ProjectInfo) o;

            if (path != null ? !path.equals(that.path) : that.path != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return path != null ? path.hashCode() : 0;
        }
    }
}
