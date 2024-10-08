package mage.tournament;

import mage.constants.TournamentPlayerState;
import mage.game.draft.DraftOptions;
import mage.game.draft.ReshuffledBoosterDraft;
import mage.game.events.TableEvent;
import mage.game.tournament.TournamentOptions;
import mage.game.tournament.TournamentPlayer;

public class ReshuffledBoosterDraftSwissTournament extends BoosterDraftSwissTournament {

    public ReshuffledBoosterDraftSwissTournament(TournamentOptions options) {
        super(options);
        currentStep = BoosterDraftSwissTournament.TournamentStep.START;
    }

    @Override
    protected void draft() {
        draft = new ReshuffledBoosterDraft((DraftOptions) options.getLimitedOptions(), getSets());
        for (TournamentPlayer player: players.values()) {
            draft.addPlayer(player.getPlayer());
            player.setState(TournamentPlayerState.DRAFTING);
        }
        tableEventSource.fireTableEvent(TableEvent.EventType.START_DRAFT, null, draft);
    }
}
