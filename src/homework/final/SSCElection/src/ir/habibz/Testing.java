package ir.habibz;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

class MyStudent implements Student {
    private String name;
    private int numberOfVotes;

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return name;
    }

    public int getNumberOfVotes() {
        return numberOfVotes;
    }

    public void submitNewVotes(int count) {
        numberOfVotes += count;
    }

    public int compareTo(Student o) {
        return Integer.compare(numberOfVotes, o.getNumberOfVotes());
    }
}


public class Testing {
    @Test
    public void testAddNominee() {
        SSCManager<MyStudent> sscManager = new SSCManager<>();
        MyStudent student = new MyStudent();
        student.setName("Ali");
        MyStudent student2 = new MyStudent();
        student2.setName("Mohammad");
        try {
            sscManager.addNominee(student);
        } catch (Exception e) {
            fail();
        }
        try {
            sscManager.addNominee(student);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Student has been previously nominated");
        }
        try {
            sscManager.addNominee(student2);
        } catch (Exception e) {
            fail();
        }
        try {
            sscManager.addNominee(student);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Student has been previously nominated");
        }
        try {
            sscManager.addNominee(student2);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Student has been previously nominated");
        }
    }

    @Test
    public void testRemoveNominee() {
        SSCManager<MyStudent> sscManager = new SSCManager<>();
        MyStudent student = new MyStudent();
        student.setName("Ali");
        MyStudent student2 = new MyStudent();
        student2.setName("Mohammad");
        try {
            sscManager.addNominee(student);
            sscManager.addNominee(student2);
        } catch (Exception e) {
            fail();
        }
        try {
            sscManager.removeFromNominees(student2);
        } catch (Exception e) {
            fail();
        }
        try {
            sscManager.removeFromNominees(student2);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Student has not been nominated");
        }
        try {
            sscManager.removeFromNominees(student);
        } catch (Exception e) {
            fail();
        }
        try {
            sscManager.removeFromNominees(student2);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Student has not been nominated");
        }
        try {
            sscManager.removeFromNominees(student);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Student has not been nominated");
        }
    }

    @Test
    public void testGetDescription() {
        SSCManager<MyStudent> sscManager = new SSCManager<>();
        MyStudent student = new MyStudent();
        student.setName("Ali");
        MyStudent student2 = new MyStudent();
        student2.setName("Mohammad");
        try {
            sscManager.addNominee(student);
        } catch (Exception e) {
            fail();
        }
        try {
            assertEquals(sscManager.getStudentDetails(student2), "Ali");
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Student has not been nominated");
        }
        try {
            sscManager.addNominee(student2);
        } catch (Exception e) {
            fail();
        }
        try {
            assertNotEquals(sscManager.getStudentDetails(student2), "Ali");
        } catch (Exception e) {
            fail();
        }
        try {
            assertEquals(sscManager.getStudentDetails(student), "Ali");
        } catch (Exception e) {
            fail();
        }
        try {
            sscManager.removeFromNominees(student2);
        } catch (Exception e) {
            fail();
        }
        try {
            assertEquals(sscManager.getStudentDetails(student2), "Mohammad");
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Student has not been nominated");
        }
        try {
            assertEquals(sscManager.getStudentDetails(student), "Ali");
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testVoting() {
        SSCManager<MyStudent> sscManager = new SSCManager<>();
        MyStudent student = new MyStudent();
        student.setName("Ali");
        MyStudent student2 = new MyStudent();
        student2.setName("Mohammad");
        try {
            sscManager.addNominee(student);
        } catch (Exception e) {
            fail();
        }
        try {
            sscManager.submitVote(student2);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Student has not been nominated");
        }
        try {
            sscManager.submitVote(student);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Election has not started yet");
        }
        try {
            assertEquals(sscManager.getResults().size(), 1);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Election has not started yet");
        }
        try {
            sscManager.startElection();
        } catch (Exception e) {
            fail();
        }
        try {
            assertEquals(sscManager.getResults().size(), 1);
            assertEquals(sscManager.getResults().get(0).getNumberOfVotes(), 0);
        } catch (Exception e) {
            fail();
        }
        try {
            sscManager.submitVote(student);
        } catch (Exception e) {
            fail();
        }
        try {
            sscManager.submitVote(student2);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Student has not been nominated");
        }
        try {
            assertEquals(sscManager.getResults().size(), 1);
            assertEquals(sscManager.getResults().get(0).getNumberOfVotes(), 1);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testManyNominees() {
        SSCManager<MyStudent> sscManager = new SSCManager<>();
        MyStudent student = new MyStudent();
        student.setName("1Mohammad");
        MyStudent student2 = new MyStudent();
        student2.setName("Mohammad2");
        MyStudent student3 = new MyStudent();
        student3.setName("Mohammad3");
        MyStudent student4 = new MyStudent();
        student4.setName("Mohammad4");
        MyStudent student5 = new MyStudent();
        student5.setName("Mohammad5");
        MyStudent student6 = new MyStudent();
        student6.setName("Mohammad6");
        MyStudent student7 = new MyStudent();
        student7.setName("Mohammad7");
        MyStudent student8 = new MyStudent();
        student8.setName("Mohammad8");
        MyStudent student9 = new MyStudent();
        student9.setName("Mohammad9");
        MyStudent student10 = new MyStudent();
        student10.setName("Mohammad10");
        MyStudent student11 = new MyStudent();
        student11.setName("Mohammad11");
        MyStudent student12 = new MyStudent();
        student12.setName("Mohammad12");
        try {
            sscManager.addNominee(student);
            sscManager.addNominee(student2);
            sscManager.addNominee(student3);
            sscManager.addNominee(student4);
            sscManager.addNominee(student5);
            sscManager.addNominee(student6);
            sscManager.addNominee(student7);
            sscManager.addNominee(student8);
            sscManager.addNominee(student9);
            sscManager.addNominee(student10);
            sscManager.addNominee(student11);
            sscManager.addNominee(student12);
            sscManager.removeFromNominees(student11);

            sscManager.startElection();
            sscManager.submitVote(student);
            sscManager.submitVote(student2);
            sscManager.submitVote(student2);
            sscManager.submitVote(student3);
            sscManager.submitVote(student3);
            sscManager.submitVote(student3);
            sscManager.submitVote(student4);
            sscManager.submitVote(student5);
            sscManager.submitVote(student4);
            sscManager.submitVote(student4);
            sscManager.submitVote(student5);
            sscManager.submitVote(student4);
            sscManager.submitVote(student5);
            sscManager.submitVote(student5);
            sscManager.submitVote(student5);
            sscManager.submitVote(student6);
            sscManager.submitVote(student6);
            sscManager.submitVote(student6);
            sscManager.submitVote(student6);
            sscManager.submitVote(student6);
            sscManager.submitVote(student6);
            sscManager.submitVote(student7);
            sscManager.submitVote(student7);
            sscManager.submitVote(student7);
            sscManager.submitVote(student7);
            sscManager.submitVote(student7);
            sscManager.submitVote(student7);
            sscManager.submitVote(student7);
            sscManager.submitVote(student8);
            sscManager.submitVote(student8);
            sscManager.submitVote(student10);
        } catch (Exception e) {
            fail();
        }
        try {
            ArrayList<MyStudent> results = sscManager.getResults();
            assertEquals(results.size(), 9);
            assertEquals(results.get(0).getDescription(), "Mohammad7");
            assertEquals(results.get(1).getDescription(), "Mohammad6");
            assertEquals(results.get(2).getDescription(), "Mohammad5");
            assertEquals(results.get(3).getDescription(), "Mohammad4");
            assertEquals(results.get(4).getDescription(), "Mohammad3");
            assertNotEquals(results.get(5).getDescription(), "Mohammad11");
        } catch (Exception e) {
            fail();
        }
    }
}