

package mage.tournament;

import mage.game.tournament.TournamentOptions;
import mage.game.tournament.TournamentSwiss;

/**
 *
 * @author LevelX2
 */
public class SealedSwissTournament extends TournamentSwiss {

    protected enum TournamentStep {
        START, OPEN_BOOSTERS, CONSTRUCT, COMPETE, WINNERS
    }

    protected TournamentStep currentStep;

    public SealedSwissTournament(TournamentOptions options) {
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
