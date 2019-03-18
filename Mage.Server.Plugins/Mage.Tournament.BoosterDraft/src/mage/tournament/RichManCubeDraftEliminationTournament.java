
package mage.tournament;

import mage.constants.TournamentPlayerState;
import mage.game.draft.DraftOptions;
import mage.game.draft.RichManCubeBoosterDraft;
import mage.game.events.TableEvent.EventType;
import mage.game.tournament.TournamentOptions;
import mage.game.tournament.TournamentPlayer;
import mage.game.tournament.TournamentSingleElimination;

/**
 *
 * @author spjspj
 */
public class RichManCubeDraftEliminationTournament extends TournamentSingleElimination {

    protected enum TournamentStep {
        START, DRAFT, CONSTRUCT, COMPETE, WINNERS
    }

    protected TournamentStep currentStep;

    public RichManCubeDraftEliminationTournament(TournamentOptions options) {
        super(options);
        currentStep = TournamentStep.START;
    }

    protected void draft() {
        draft = new RichManCubeBoosterDraft((DraftOptions) options.getLimitedOptions(), getSets());
        for (TournamentPlayer player : players.values()) {
            draft.addPlayer(player.getPlayer());
            player.setState(TournamentPlayerState.DRAFTING);
        }
        tableEventSource.fireTableEvent(EventType.START_DRAFT, null, draft);
    }

    @Override
    public void nextStep() {
        if (isAbort()) {
            currentStep = TournamentStep.COMPETE;
        }
        switch (currentStep) {
            case START:
                currentStep = TournamentStep.DRAFT;
                draft();
                break;
            case DRAFT:
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
