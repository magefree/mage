package mage.tournament;

import mage.constants.TournamentPlayerState;
import mage.game.draft.DraftOptions;
import mage.game.draft.RichManCubeBoosterDraft;
import mage.game.events.TableEvent;
import mage.game.tournament.TournamentOptions;
import mage.game.tournament.TournamentPlayer;

/**
 *
 * @author glee-
 */
public class RichManCubeDraftSwissTournament extends BoosterDraftSwissTournament {

    public RichManCubeDraftSwissTournament(TournamentOptions options) {
        super(options);
    }

    @Override
    protected void draft() {
        draft = new RichManCubeBoosterDraft((DraftOptions) options.getLimitedOptions(), getSets());
        for (TournamentPlayer player : players.values()) {
            draft.addPlayer(player.getPlayer());
            player.setState(TournamentPlayerState.DRAFTING);
        }
        tableEventSource.fireTableEvent(TableEvent.EventType.START_DRAFT, null, draft);
    }

}
