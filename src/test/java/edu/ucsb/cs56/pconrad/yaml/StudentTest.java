package edu.ucsb.cs56.pconrad.yaml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.Before;

public class StudentTest {

    @Test(expected = IllegalArgumentException.class)
    public void test_set_negative_perm() {
	Student s = new Student();
	s.setPerm(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_set_too_big_perm() {
	Student s = new Student();
	s.setPerm(10000000);
    }

    @Test
    public void test_set_and_get_perm() {
	Student s = new Student();
	s.setPerm(1234567);
	assertEquals(1234567, s.getPerm());
    }

    @Test
    public void test_set_and_get_name() {
	Student s = new Student();
	s.setName("Chris Gaucho");
	assertEquals("Chris Gaucho", s.getName());
    }

    @Test
    public void test_set_and_get_majors() {
	Student s = new Student();
	String [] majors = {"CMPSC", "MATH"};
	s.setMajors(majors);
	assertEquals(majors,s.getMajors());
    }

}
