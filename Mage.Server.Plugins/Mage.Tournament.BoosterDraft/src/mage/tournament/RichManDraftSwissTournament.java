package mage.tournament;

import mage.constants.TournamentPlayerState;
import mage.game.draft.DraftOptions;
import mage.game.draft.RichManBoosterDraft;
import mage.game.events.TableEvent;
import mage.game.tournament.TournamentOptions;
import mage.game.tournament.TournamentPlayer;

/**
 *
 * @author glee-
 */
public class RichManDraftSwissTournament extends BoosterDraftSwissTournament {
    public RichManDraftSwissTournament(TournamentOptions options) {
        super(options);
    }

    @Override
    protected void draft() {
        draft = new RichManBoosterDraft((DraftOptions) options.getLimitedOptions(), getSets());
        for (TournamentPlayer player : players.values()) {
            draft.addPlayer(player.getPlayer());
            player.setState(TournamentPlayerState.DRAFTING);
        }
        tableEventSource.fireTableEvent(TableEvent.EventType.START_DRAFT, null, draft);
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
