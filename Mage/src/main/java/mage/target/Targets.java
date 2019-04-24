
package mage.target;

import mage.abilities.Ability;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Targets extends ArrayList<Target> {

    public Targets() {
    }

    public Targets(Target target) {
        this.add(target);
    }

    public Targets(final Targets targets) {
        for (Target target : targets) {
            this.add(target.copy());
        }
    }

    public List<Target> getUnchosen() {
        return stream().filter(target -> !target.isChosen()).collect(Collectors.toList());
    }

    public void clearChosen() {
        for (Target target : this) {
            target.clearChosen();
        }
    }

    public boolean isChosen() {
        return stream().allMatch(Target::isChosen);
    }

    public boolean choose(Outcome outcome, UUID playerId, UUID sourceId, Game game) {
        if (this.size() > 0) {
            if (!canChoose(sourceId, playerId, game)) {
                return false;
            }
            while (!isChosen()) {
                Target target = this.getUnchosen().get(0);
                if (!target.choose(outcome, playerId, sourceId, game)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean chooseTargets(Outcome outcome, UUID playerId, Ability source, boolean noMana, Game game) {
        if (this.size() > 0) {
            if (!canChoose(source.getSourceId(), playerId, game)) {
                return false;
            }
            int state = game.bookmarkState();
            while (!isChosen()) {
                Target target = this.getUnchosen().get(0);
                UUID targetController = playerId;
                if (target.getTargetController() != null) { // some targets can have controller different than ability controller
                    targetController = target.getTargetController();
                }
                if (noMana) { // if cast without mana (e.g. by suspend you may not be able to cancel the casting if you are able to cast it
                    target.setRequired(true);
                }
                if (!target.chooseTarget(outcome, targetController, source, game)) {
                    return false;
                }
                // Check if there are some rules for targets are violated, if so reset the targets and start again
                if (this.getUnchosen().isEmpty()
                        && game.replaceEvent(new GameEvent(GameEvent.EventType.TARGETS_VALID, source.getSourceId(), source.getSourceId(), source.getControllerId()), source)) {
                    game.restoreState(state, "Targets");
                    clearChosen();
                }
            }
        }
        return true;
    }

    public boolean stillLegal(Ability source, Game game) {
        // 608.2
        // The spell or ability is countered if all its targets, for every instance of the word "target," are now illegal
        int illegalCount = (int) stream().filter(target -> !target.isLegal(source, game)).count();

        // it is legal when either there is no target or not all targets are illegal
        return this.isEmpty() || this.size() != illegalCount;
    }

    /**
     * Checks if there are enough targets that can be chosen. Should only be
     * used for Ability targets since this checks for protection, shroud etc.
     *
     * @param sourceId - the target event source
     * @param sourceControllerId - controller of the target event source
     * @param game
     * @return - true if enough valid targets exist
     */
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        return stream().allMatch(target -> target.canChoose(sourceId, sourceControllerId, game));
    }

    /**
     * Checks if there are enough objects that can be selected. Should not be
     * used for Ability targets since this does not check for protection, shroud
     * etc.
     *
     * @param sourceControllerId - controller of the select event
     * @param game
     * @return - true if enough valid objects exist
     */
    public boolean canChoose(UUID sourceControllerId, Game game) {
        return stream().allMatch(target -> target.canChoose(sourceControllerId, game));
    }

    public UUID getFirstTarget() {
        if (this.size() > 0) {
            return this.get(0).getFirstTarget();
        }
        return null;
    }

    public Targets copy() {
        return new Targets(this);
    }
}
