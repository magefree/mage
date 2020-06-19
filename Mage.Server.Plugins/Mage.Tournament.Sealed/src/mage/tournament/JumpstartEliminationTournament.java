

package mage.tournament;

import mage.game.tournament.TournamentOptions;
import mage.game.tournament.TournamentSingleElimination;

public class JumpstartEliminationTournament extends TournamentSingleElimination {

    protected enum TournamentStep {
        START, OPEN_BOOSTERS, CONSTRUCT, COMPETE, WINNERS
    }

    protected TournamentStep currentStep;

    public JumpstartEliminationTournament(TournamentOptions options) {
        super(options);
        currentStep = TournamentStep.START;
    }

    @Override
    public void nextStep() {
        switch (currentStep) {
            case START:
                currentStep = TournamentStep.OPEN_BOOSTERS;
                openBoosters();
                break;
            case OPEN_BOOSTERS:
                currentStep = TournamentStep.CONSTRUCT;
                construct();
                break;
            case CONSTRUCT:
                currentStep = TournamentStep.COMPETE;
                runTournament();
                break;
            case COMPETE:
                currentStep = TournamentStep.WINNERS;
                winners();
                end();
                break;
        }
    }

}
