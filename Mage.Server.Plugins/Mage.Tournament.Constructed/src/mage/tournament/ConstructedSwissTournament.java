
package mage.tournament;

import mage.game.tournament.TournamentOptions;
import mage.game.tournament.TournamentSwiss;

/**
 *
 * @author LevelX2
 */

public class ConstructedSwissTournament extends TournamentSwiss {

    protected enum TournamentStep {
        START, COMPETE, WINNERS
    }

    protected TournamentStep currentStep;

    public ConstructedSwissTournament(TournamentOptions options) {
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
