

package mage.tournament;

import mage.constants.TournamentPlayerState;
import mage.game.draft.DraftOptions;
import mage.game.events.TableEvent.EventType;
import mage.game.tournament.TournamentPlayer;
import mage.game.draft.RandomBoosterDraft;
import mage.game.tournament.TournamentOptions;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class RandomBoosterDraftSwissTournament extends BoosterDraftSwissTournament {

    public RandomBoosterDraftSwissTournament(TournamentOptions options) {
        super(options);
        currentStep = TournamentStep.START;
    }

    @Override
    protected void draft() {
        draft = new RandomBoosterDraft((DraftOptions) options.getLimitedOptions(), getSets());
        for (TournamentPlayer player: players.values()) {
            draft.addPlayer(player.getPlayer());
            player.setState(TournamentPlayerState.DRAFTING);
        }
        tableEventSource.fireTableEvent(EventType.START_DRAFT, null, draft);
    }
}
