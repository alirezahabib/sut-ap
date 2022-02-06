package ir.habibz;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class SSCManager<Nominee extends Student> {
    private final ArrayList<Nominee> nominees = new ArrayList<>();
    private boolean electionRunning = false;

    private void checkNominated(Nominee nominee) throws Exception {
        if (!nominees.contains(nominee)) throw new Exception("Student has not been nominated");
    }

    private void checkElectionStarted() throws Exception {
        if (!electionRunning) throw new Exception("Election has not started yet");
    }

    public void addNominee(Nominee nominee) throws Exception {
        if (nominees.contains(nominee)) throw new Exception("Student has been previously nominated");
        nominees.add(nominee);
    }

    public void removeFromNominees(Nominee nominee) throws Exception {
        checkNominated(nominee);
        nominees.remove(nominee);
    }

    public String getStudentDetails(Nominee nominee) throws Exception {
        checkNominated(nominee);
        return nominee.getDescription();
    }

    public void startElection() throws Exception {
        if (electionRunning) throw new Exception("Election is currently running");
        electionRunning = true;
    }

    public void submitVote(Nominee nominee) throws Exception {
        checkNominated(nominee);
        checkElectionStarted();
        nominee.submitNewVotes(1);
    }

    public ArrayList<Nominee> getResults() throws Exception {
        checkElectionStarted();

        nominees.sort(Comparator.reverseOrder());
        return nominees.stream().limit(9).collect(Collectors.toCollection(ArrayList::new));
    }
}
