package edu.ucsb.cs56.pconrad.yaml;

public class Student {

    private String [] majors = {};
    private int perm = 0;
    private String name = "";

    public String getName() { return this.name; }
    public int getPerm() { return this.perm; }

    public String [] getMajors() { return this.majors; }

    public void setMajors(String [] majors) {
	this.majors = majors;
    }

    public void setPerm(int perm) {
	if (perm < 0 || perm > 9999999)
	    throw new IllegalArgumentException("invalid perm value");
	this.perm = perm;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String toString() {
	String result = 
	    "Student[name=" + this.name +
	    ",perm=" + this.perm +
	    ",majors=" + this.majors +
	    "]";
	return "stub";
    }

    public boolean equals(Object o) {
	if (o == null ||
	    ! (o instanceof Student))
	    return false;
	Student s = (Student) o;
	return this.perm == s.perm;	
    }

    public int hashCode() {
	return this.perm;
    }

}
