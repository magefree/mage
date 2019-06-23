
package mage.game.turn;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.UUID;
import mage.constants.PhaseStep;
import mage.constants.TurnPhase;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TurnMods extends ArrayList<TurnMod> {

    public TurnMods() {
    }

    public TurnMods(final TurnMods mods) {
        for (TurnMod mod : mods) {
            this.add(mod.copy());
        }
    }

    public UUID getExtraTurn(UUID playerId) {
        ListIterator<TurnMod> it = this.listIterator(this.size());
        while (it.hasPrevious()) {
            TurnMod turnMod = it.previous();
            if (turnMod.isExtraTurn() && turnMod.getPlayerId().equals(playerId)) {
                it.remove();
                return turnMod.getId();
            }
        }
        return null;
    }

    public TurnMod getNextExtraTurn() {
        ListIterator<TurnMod> it = this.listIterator(this.size());
        while (it.hasPrevious()) {
            TurnMod turnMod = it.previous();
            if (turnMod.isExtraTurn()) {
                it.remove();
                return turnMod;
            }
        }
        return null;
    }

    public boolean skipTurn(UUID playerId) {
        ListIterator<TurnMod> it = this.listIterator(this.size());
        while (it.hasPrevious()) {
            TurnMod turnMod = it.previous();
            if (turnMod.isSkipTurn() && turnMod.getPlayerId().equals(playerId)) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    public UUID controlsTurn(UUID playerId) {
        ListIterator<TurnMod> it = this.listIterator(this.size());
        TurnMod controlPlayerTurnMod = null;
        while (it.hasPrevious()) {
            TurnMod turnMod = it.previous();
            if (turnMod.getNewControllerId() != null && turnMod.getPlayerId().equals(playerId)) {
                controlPlayerTurnMod = turnMod;
                it.remove();
            }
        }
        // now delete all other effects that control current active player - control next turn of player effects are not cumulative
        it = this.listIterator(this.size());
        while (it.hasPrevious()) {
            TurnMod turnMod = it.previous();
            if (turnMod.getNewControllerId() != null && turnMod.getPlayerId().equals(playerId)) {
                it.remove();
            }
        }
        // apply subsequent turn mod
        if (controlPlayerTurnMod != null && controlPlayerTurnMod.getSubsequentTurnMod() != null) {
            this.add(controlPlayerTurnMod.getSubsequentTurnMod());
        }
        return controlPlayerTurnMod != null ? controlPlayerTurnMod.getNewControllerId() : null;
    }

    public Step extraStep(UUID playerId, PhaseStep afterStep) {
        ListIterator<TurnMod> it = this.listIterator(this.size());
        while (it.hasPrevious()) {
            TurnMod turnMod = it.previous();
            if (turnMod.getExtraStep() != null && turnMod.getPlayerId().equals(playerId) && (turnMod.getAfterStep() == null || turnMod.getAfterStep() == afterStep)) {
                it.remove();
                return turnMod.getExtraStep();
            }
        }
        return null;
    }

    public boolean skipStep(UUID playerId, PhaseStep step) {
        if (step != null) {
            ListIterator<TurnMod> it = this.listIterator(this.size());
            while (it.hasPrevious()) {
                TurnMod turnMod = it.previous();
                if (turnMod.getSkipStep() != null) {
                    if (turnMod.getPlayerId() != null && turnMod.getPlayerId().equals(playerId)) {
                        if (turnMod.getSkipStep() == step) {
                            it.remove();
                            return true;

                        }
                    }
                }
            }
        }
        return false;
    }

    public TurnMod extraPhase(UUID playerId, TurnPhase afterPhase) {
        ListIterator<TurnMod> it = this.listIterator(this.size());
        while (it.hasPrevious()) {
            TurnMod turnMod = it.previous();
            if (turnMod.getExtraPhase() != null && turnMod.getPlayerId().equals(playerId) && (turnMod.getAfterPhase() == null || turnMod.getAfterPhase() == afterPhase)) {
                it.remove();
                return turnMod;
            }
        }
        return null;
    }

    public boolean skipPhase(UUID playerId, TurnPhase phase) {
        ListIterator<TurnMod> it = this.listIterator(this.size());
        while (it.hasPrevious()) {
            TurnMod turnMod = it.previous();
            if (turnMod.getSkipPhase() != null && turnMod.getPlayerId().equals(playerId) && turnMod.getSkipPhase() == phase) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    public TurnMods copy() {
        return new TurnMods(this);
    }

}
