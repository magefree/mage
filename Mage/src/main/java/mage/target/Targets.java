package mage.target;

import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.util.Copyable;
import mage.util.DebugUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class Targets extends ArrayList<Target> implements Copyable<Targets> {

    private boolean isReadOnly = false; // runtime protect from not working targets modification, e.g. in composite costs

    public Targets() {
        // fast constructor
    }

    public Targets(Target... targets) {
        this.addAll(Arrays.asList(targets));
    }

    protected Targets(final Targets targets) {
        this.ensureCapacity(targets.size());
        for (Target target : targets) {
            this.add(target.copy());
        }
        this.isReadOnly = targets.isReadOnly;
    }

    public Targets withReadOnly() {
        this.isReadOnly = true;
        return this;
    }

    public Target getNextUnchosen(Game game) {
        return getNextUnchosen(game, 0);
    }

    public Target getNextUnchosen(Game game, int unchosenIndex) {
        List<Target> res = stream()
                .filter(target -> !target.isChoiceSelected())
                .collect(Collectors.toList());
        return unchosenIndex < res.size() ? res.get(unchosenIndex) : null;
    }

    public boolean isChoiceCompleted(Game game) {
        return stream().allMatch(t -> t.isChoiceCompleted(game));
    }

    public void clearChosen() {
        for (Target target : this) {
            target.clearChosen();
        }
    }

    public boolean isChosen(Game game) {
        return stream().allMatch(t -> t.isChosen(game));
    }

    public boolean choose(Outcome outcome, UUID playerId, UUID sourceId, Ability source, Game game) {
        Player player = game.getPlayer(playerId);
        if (player == null) {
            return false;
        }

        // in test mode some targets can be predefined already, e.g. by cast/activate command
        // so do not clear chosen status here

        if (this.size() > 0) {
            do {
                // stop on disconnect or nothing to choose
                if (!player.canRespond() || !canChoose(playerId, source, game)) {
                    break;
                }

                // stop on complete
                Target target = this.getNextUnchosen(game);
                if (target == null) {
                    break;
                }

                // stop on cancel/done
                if (!target.choose(outcome, playerId, sourceId, source, game)) {
                    if (!target.isChosen(game)) {
                        break;
                    }
                }

                // target done, can take next one
            } while (true);
        }

        if (DebugUtil.GAME_SHOW_CHOOSE_TARGET_LOGS && !game.isSimulation()) {
            printDebugTargets("choose finish", this, source, game);
        }

        return isChosen(game);
    }

    public boolean chooseTargets(Outcome outcome, UUID playerId, Ability source, boolean noMana, Game game, boolean canCancel) {
        Player player = game.getPlayer(playerId);
        if (player == null) {
            return false;
        }

        // in test mode some targets can be predefined already, e.g. by cast/activate command
        // so do not clear chosen status here

        if (this.size() > 0) {
            do {
                // stop on disconnect or nothing to choose
                if (!player.canRespond() || !canChoose(playerId, source, game)) {
                    break;
                }

                // stop on complete
                Target target = this.getNextUnchosen(game);
                if (target == null) {
                    break;
                }

                // some targets can have controller different than ability controller
                UUID targetController = playerId;
                if (target.getTargetController() != null) {
                    targetController = target.getTargetController();
                }

                // disable cancel button - if cast without mana (e.g. by suspend you may not be able to cancel the casting if you are able to cast it
                if (noMana) {
                    target.setRequired(true);
                }
                // enable cancel button
                if (canCancel) {
                    target.setRequired(false);
                }

                // stop on cancel/done
                if (!target.chooseTarget(outcome, targetController, source, game)) {
                    if (!target.isChosen(game)) {
                        break;
                    }
                }

                // reset on wrong restrictions and start from scratch
                if (this.getNextUnchosen(game) == null
                        && game.replaceEvent(new GameEvent(GameEvent.EventType.TARGETS_VALID, source.getSourceId(), source, source.getControllerId()), source)) {
                    clearChosen();
                }

                // target done, can take next one
            } while (true);
        }

        if (DebugUtil.GAME_SHOW_CHOOSE_TARGET_LOGS && !game.isSimulation()) {
            printDebugTargets("chooseTargets finish", this, source, game);
        }

        return isChosen(game);
    }

    public static void printDebugTargets(String name, Targets targets, Ability source, Game game) {
        List<String> output = new ArrayList<>();
        printDebugTargets(name, targets, source, game, output);
        output.forEach(System.out::println);
    }

    public static void printDebugTargets(String name, Targets targets, Ability source, Game game, List<String> output) {
        output.add("");
        output.add(name + ":");
        output.add(String.format("* chosen: %s", targets.isChosen(game) ? "yes" : "no"));
        output.add(String.format("* ability: %s", source));
        for (int i = 0; i < targets.size(); i++) {
            Target target = targets.get(i);
            output.add(String.format("* target %d: %s", i + 1, target));
            if (target.getTargets().isEmpty()) {
                output.add("  - no choices");
            } else {
                for (int j = 0; j < target.getTargets().size(); j++) {
                    UUID targetId = target.getTargets().get(j);
                    String targetInfo;
                    Player targetPlayer = game.getPlayer(targetId);
                    if (targetPlayer != null) {
                        targetInfo = targetPlayer.toString();
                    } else {
                        MageObject targetObject = game.getObject(targetId);
                        if (targetObject != null) {
                            targetInfo = targetObject.toString();
                        } else {
                            targetInfo = "unknown " + targetId;
                        }
                    }
                    if (target instanceof TargetAmount) {
                        output.add(String.format("  - choice %d.%d: amount %d, %s", i + 1, j + 1, target.getTargetAmount(targetId), targetInfo));
                    } else {
                        output.add(String.format("  - choice %d.%d: %s", i + 1, j + 1, targetInfo));
                    }
                }
            }
        }
    }

    public boolean stillLegal(Ability source, Game game) {
        // 608.2
        // The spell or ability is countered if all its targets, for every instance of the word "target," are now illegal
        int illegalCount = (int) stream().filter(target -> !target.isLegal(source, game)).count();

        // it is legal when either there is no target or not all targets are illegal
        return this.isEmpty() || this.size() != illegalCount;
    }

    /**
     * For target choose
     * <p>
     * Checks if there are enough targets that can be chosen. Should only be
     * used for Ability targets since this checks for protection, shroud etc.
     *
     * @param sourceControllerId - controller of the target event source
     * @param source
     * @param game
     * @return - true if enough valid targets exist
     */
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        return stream().allMatch(target -> target.canChoose(sourceControllerId, source, game));
    }

    /**
     * For non target choose (e.g. cost pay)
     * <p>
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

    @Override
    public Targets copy() {
        return new Targets(this);
    }

    private void checkReadOnlyModification() {
        if (this.isReadOnly) {
            throw new IllegalArgumentException("Wrong code usage: you can't modify read only targets list, e.g. from composite costs");
        }
    }

    @Override
    public boolean add(Target target) {
        checkReadOnlyModification();
        return super.add(target);
    }

    @Override
    public void add(int index, Target element) {
        checkReadOnlyModification();
        super.add(index, element);
    }

    @Override
    public boolean addAll(Collection<? extends Target> c) {
        checkReadOnlyModification();
        return super.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends Target> c) {
        checkReadOnlyModification();
        return super.addAll(index, c);
    }

    @Override
    public void clear() {
        checkReadOnlyModification();
        super.clear();
    }
}
