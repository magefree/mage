

package mage.tournament;

import mage.constants.TournamentPlayerState;
import mage.game.draft.BoosterDraft;
import mage.game.draft.DraftOptions;
import mage.game.events.TableEvent;
import mage.game.tournament.TournamentOptions;
import mage.game.tournament.TournamentPlayer;
import mage.game.tournament.TournamentSwiss;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class BoosterDraftSwissTournament extends TournamentSwiss {

    protected enum TournamentStep {
        START, DRAFT, CONSTRUCT, COMPETE, WINNERS
    }

    protected BoosterDraftSwissTournament.TournamentStep currentStep;

    public BoosterDraftSwissTournament(TournamentOptions options) {
        super(options);
        currentStep = BoosterDraftSwissTournament.TournamentStep.START;
    }

    protected void draft() {
        draft = new BoosterDraft((DraftOptions) options.getLimitedOptions(), getSets());
        for (TournamentPlayer player: players.values()) {
            draft.addPlayer(player.getPlayer());
            player.setState(TournamentPlayerState.DRAFTING);
        }
        tableEventSource.fireTableEvent(TableEvent.EventType.START_DRAFT, null, draft);
    }



    @Override
    public void nextStep() {
        switch (currentStep) {
            case START:
                currentStep = BoosterDraftSwissTournament.TournamentStep.DRAFT;
                draft();
                break;
            case DRAFT:
                currentStep = BoosterDraftSwissTournament.TournamentStep.CONSTRUCT;
                construct();
                break;
            case CONSTRUCT:
                currentStep = BoosterDraftSwissTournament.TournamentStep.COMPETE;
                runTournament();
                break;
            case COMPETE:
                currentStep = BoosterDraftSwissTournament.TournamentStep.WINNERS;
                winners();
                end();
                break;
        }
    }


}
