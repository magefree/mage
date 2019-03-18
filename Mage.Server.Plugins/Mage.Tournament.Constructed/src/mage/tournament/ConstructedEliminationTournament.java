

package mage.tournament;

import mage.game.tournament.TournamentOptions;
import mage.game.tournament.TournamentSingleElimination;

/**
 *
 * @author LevelX2
 */
public class ConstructedEliminationTournament extends TournamentSingleElimination {

    protected enum TournamentStep {
        START, COMPETE, WINNERS
    }

    protected TournamentStep currentStep;

    public ConstructedEliminationTournament(TournamentOptions options) {
        super(options);
        currentStep = TournamentStep.START;
    }

    @Override
    public void nextStep() {
        switch (currentStep) {
            case START:
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
