package in.ac.bitspilani.assignment.ss.courseservice.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class Subject {

    @Id
    private int id;
    private String name;
    private int semesterOffered;
    private boolean core;
    private boolean scheduled;

    public Subject(){

    }

    public Subject(int id, String name, int semesterOffered, boolean core, boolean scheduled) {
        this.id = id;
        this.name = name;
        this.semesterOffered = semesterOffered;
        this.core = core;
        this.scheduled = scheduled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSemesterOffered() {
        return semesterOffered;
    }

    public void setSemesterOffered(int semesterOffered) {
        this.semesterOffered = semesterOffered;
    }

    public boolean isCore() {
        return core;
    }

    public void setCore(boolean core) {
        this.core = core;
    }

    public boolean isScheduled() {
        return scheduled;
    }

    public void setScheduled(boolean scheduled) {
        this.scheduled = scheduled;
    }
}
