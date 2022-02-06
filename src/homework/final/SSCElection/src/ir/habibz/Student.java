package ir.habibz;

public interface Student extends Comparable<Student> {
    String getDescription();
    int getNumberOfVotes();
    void submitNewVotes(int count);
    int compareTo(Student o);
}
