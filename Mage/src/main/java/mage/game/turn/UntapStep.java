

package mage.game.turn;

import mage.constants.PhaseStep;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.watchers.common.CastSpellLastTurnWatcher;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class UntapStep extends Step {

    public UntapStep() {
        super(PhaseStep.UNTAP, false);
        this.stepEvent = EventType.UNTAP_STEP;
        this.preStepEvent = EventType.UNTAP_STEP_PRE;
        this.postStepEvent = EventType.UNTAP_STEP_POST;
    }

    protected UntapStep(final UntapStep step) {
        super(step);
    }

    @Override
    public void beginStep(Game game, UUID activePlayerId) {
        super.beginStep(game, activePlayerId);

        // 726.2.
        // As the second part of the untap step, the game checks the previous turn to see
        // if the game’s day/night designation should change. See rule 502, “Untap Step.”
        //
        // Before a player untaps their permanents during the untap step, the game checks to see
        // if the day/night designation should change. (2021-09-24)
        handleDayNight(game);

        Player activePlayer = game.getPlayer(activePlayerId);
        //20091005 - 502.1/703.4a
        activePlayer.phasing(game);
        //20091005 - 502.2/703.4b
        activePlayer.untap(game);
        game.applyEffects();
    }

    @Override
    public UntapStep copy() {
        return new UntapStep(this);
    }

    private void handleDayNight(Game game) {
        if (!game.hasDayNight() || game.getTurnNum() <= 1) {
            return;
        }
        int previousSpells = game
                .getState()
                .getWatcher(CastSpellLastTurnWatcher.class)
                .getActivePlayerPrevTurnCount();
        if (game.checkDayNight(true) && previousSpells == 0) {
            // 726.2a
            // If it’s day and the previous turn’s active player didn’t cast any spells during that turn,
            // it becomes night. Multiplayer games using the shared team turns option (see rule 805)
            // use a modified rule: if it’s day and no player from the previous turn’s active team cast a
            // spell during that turn, it becomes night.
            game.setDaytime(false);
        } else if (game.checkDayNight(false) && previousSpells >= 2) {
            // 726.2b
            // If it’s night, and previous turn’s active player cast two or more spells during the previous turn,
            // it becomes day. Multiplayer games using the shared team turns option (see rule 805) use a modified
            // rule: if it’s night and any player from the previous turn’s active team cast two or more spells
            // during that turn, it becomes day.
            game.setDaytime(true);
        }
    }
}
