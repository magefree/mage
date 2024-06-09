package mage.game.turn;

import mage.constants.PhaseStep;
import mage.constants.TurnPhase;
import mage.util.Copyable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.UUID;

/**
 * Turn, phase and step modification for extra/skip (use it for one time mod only)
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TurnMods extends ArrayList<TurnMod> implements Serializable, Copyable<TurnMods> {

    public TurnMods() {
    }

    private TurnMods(final TurnMods mods) {
        for (TurnMod mod : mods) {
            this.add(mod.copy());
        }
    }

    public TurnMods copy() {
        return new TurnMods(this);
    }

    @Override
    public boolean add(TurnMod turnMod) {
        if (!turnMod.isLocked()) {
            throw new IllegalStateException("Wrong code usage: you must prepare turn mode with modification");
        }
        return super.add(turnMod);
    }

    public TurnMod useNextExtraTurn() {
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

    public TurnMod useNextSkipTurn(UUID playerId) {
        ListIterator<TurnMod> it = this.listIterator(this.size());
        while (it.hasPrevious()) {
            TurnMod turnMod = it.previous();
            if (turnMod.isSkipTurn() && turnMod.getPlayerId().equals(playerId)) {
                it.remove();
                return turnMod;
            }
        }
        return null;
    }

    public TurnMod useNextNewController(UUID playerId) {
        TurnMod lastNewControllerMod = null;

        // find last/actual mod
        ListIterator<TurnMod> it = this.listIterator(this.size());
        while (it.hasPrevious()) {
            TurnMod turnMod = it.previous();
            if (turnMod.getNewControllerId() != null && turnMod.getPlayerId().equals(playerId)) {
                lastNewControllerMod = turnMod;
                it.remove();
            }
        }

        // delete all other outdated mods
        it = this.listIterator(this.size());
        while (it.hasPrevious()) {
            TurnMod turnMod = it.previous();
            if (turnMod.getNewControllerId() != null && turnMod.getPlayerId().equals(playerId)) {
                it.remove();
            }
        }

        // add subsequent turn mod to execute after current
        if (lastNewControllerMod != null && lastNewControllerMod.getSubsequentTurnMod() != null) {
            this.add(lastNewControllerMod.getSubsequentTurnMod());
        }

        return lastNewControllerMod;
    }

    public TurnMod useNextExtraStep(UUID playerId, PhaseStep afterStep) {
        ListIterator<TurnMod> it = this.listIterator(this.size());
        while (it.hasPrevious()) {
            TurnMod turnMod = it.previous();
            if (turnMod.getExtraStep() != null && turnMod.getPlayerId().equals(playerId) && (turnMod.getAfterStep() == null || turnMod.getAfterStep() == afterStep)) {
                it.remove();
                return turnMod;
            }
        }
        return null;
    }

    public TurnMod useNextSkipStep(UUID playerId, PhaseStep step) {
        if (step != null) {
            ListIterator<TurnMod> it = this.listIterator(this.size());
            while (it.hasPrevious()) {
                TurnMod turnMod = it.previous();
                if (turnMod.getSkipStep() != null) {
                    if (turnMod.getPlayerId() != null && turnMod.getPlayerId().equals(playerId)) {
                        if (turnMod.getSkipStep() == step) {
                            it.remove();
                            return turnMod;
                        }
                    }
                }
            }
        }
        return null;
    }

    public TurnMod useNextExtraPhase(UUID playerId, TurnPhase afterPhase) {
        ListIterator<TurnMod> it = this.listIterator(this.size());
        while (it.hasPrevious()) {
            TurnMod turnMod = it.previous();
            if (turnMod.getExtraPhase() != null
                    && turnMod.getPlayerId().equals(playerId)
                    && (turnMod.getAfterPhase() == null || turnMod.getAfterPhase() == afterPhase)) {
                it.remove();
                return turnMod;
            }
        }
        return null;
    }

    public TurnMod useNextSkipPhase(UUID playerId, TurnPhase phase) {
        ListIterator<TurnMod> it = this.listIterator(this.size());
        while (it.hasPrevious()) {
            TurnMod turnMod = it.previous();
            if (turnMod.getSkipPhase() != null && turnMod.getPlayerId().equals(playerId) && turnMod.getSkipPhase() == phase) {
                it.remove();
                return turnMod;
            }
        }
        return null;
    }
}
