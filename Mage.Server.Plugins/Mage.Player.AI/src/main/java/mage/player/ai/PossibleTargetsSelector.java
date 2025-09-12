package mage.player.ai;

import mage.MageItem;
import mage.abilities.Ability;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.ControllableOrOwnerable;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyardBattlefieldOrStack;
import mage.target.common.TargetDiscard;

import java.util.*;
import java.util.stream.Collectors;

/**
 * AI related code - find possible targets and sort it due priority
 *
 * @author JayDi85
 */
public class PossibleTargetsSelector {

    Outcome outcome;
    Target target;
    UUID abilityControllerId;
    Ability source;
    Game game;

    PossibleTargetsComparator comparators;

    // possible targets lists
    List<MageItem> me = new ArrayList<>();
    List<MageItem> opponents = new ArrayList<>();
    List<MageItem> any = new ArrayList<>(); // for outcomes with any target like copy

    public PossibleTargetsSelector(Outcome outcome, Target target, UUID abilityControllerId, Ability source, Game game) {
        this.outcome = outcome;
        this.target = target;
        this.abilityControllerId = abilityControllerId;
        this.source = source;
        this.game = game;
        this.comparators = new PossibleTargetsComparator(abilityControllerId, game);
    }

    public void findNewTargets(Set<UUID> fromTargetsList) {
        // collect new valid targets
        List<MageItem> found = target.possibleTargets(abilityControllerId, source, game, fromTargetsList).stream()
                .filter(id -> !target.contains(id))
                .map(id -> {
                    Player player = game.getPlayer(id);
                    if (player != null) {
                        return player;
                    } else {
                        return game.getObject(id);
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        // split targets between me and opponents
        found.forEach(item -> {
            if (isMyItem(abilityControllerId, item)) {
                this.me.add(item);
            } else {
                this.opponents.add(item);
            }
            this.any.add(item);
        });

        if (target instanceof TargetDiscard) {
            // sort due unplayable
            sortByUnplayableAndUseless();
        } else {
            // sort due good/bad outcome
            sortByMostValuableTargets();
        }
    }

    /**
     * Sorting for any good/bad effects
     */
    private void sortByMostValuableTargets() {
        if (isGoodEffect()) {
            // for good effect must choose the biggest objects
            this.me.sort(comparators.ANY_MOST_VALUABLE_FIRST);
            this.opponents.sort(comparators.ANY_MOST_VALUABLE_LAST);
            this.any.sort(comparators.ANY_MOST_VALUABLE_FIRST);
        } else {
            // for bad effect must choose the smallest objects
            this.me.sort(comparators.ANY_MOST_VALUABLE_LAST);
            this.opponents.sort(comparators.ANY_MOST_VALUABLE_FIRST);
            this.any.sort(comparators.ANY_MOST_VALUABLE_LAST);
        }
    }

    /**
     * Sorting for discard
     */
    private void sortByUnplayableAndUseless() {
        // used
        // no good or bad effect - you must choose
        comparators.findPlayableItems();
        this.me.sort(comparators.ANY_UNPLAYABLE_AND_USELESS);
        this.opponents.sort(comparators.ANY_UNPLAYABLE_AND_USELESS);
        this.any.sort(comparators.ANY_UNPLAYABLE_AND_USELESS);
    }

    /**
     * Priority targets. Try to use as much as possible.
     */
    public List<MageItem> getGoodTargets() {
        if (isAnyEffect()) {
            return this.any;
        }

        if (isGoodEffect()) {
            return this.me;
        } else {
            return this.opponents;
        }
    }

    /**
     * Optional targets. Try to ignore bad targets (e.g. opponent's creatures for your good effect).
     */
    public List<MageItem> getBadTargets() {
        if (isAnyEffect()) {
            return Collections.emptyList();
        }

        if (isGoodEffect()) {
            return this.opponents;
        } else {
            return this.me;
        }
    }

    public List<MageItem> getAny() {
        return this.any;
    }

    public static boolean isMyItem(UUID abilityControllerId, MageItem item) {
        if (item instanceof Player) {
            return item.getId().equals(abilityControllerId);
        } else if (item instanceof ControllableOrOwnerable) {
            return ((ControllableOrOwnerable) item).getControllerOrOwnerId().equals(abilityControllerId);
        }
        return false;
    }

    private boolean isAnyEffect() {
        boolean isAnyEffect = outcome.anyTargetHasSameValue();

        if (hasGoodExile()) {
            isAnyEffect = true;
        }

        return isAnyEffect;
    }

    private boolean isGoodEffect() {
        boolean isGoodEffect = outcome.isGood();

        if (hasGoodExile()) {
            isGoodEffect = true;
        }

        return isGoodEffect;
    }

    private boolean hasGoodExile() {
        // exile workaround: exile is bad, but exile from library or graveyard in most cases is good
        // (more exiled -- more good things you get, e.g. delve's pay or search cards with same name)
        if (outcome == Outcome.Exile) {
            if (Zone.GRAVEYARD.match(target.getZone())
                    || Zone.LIBRARY.match(target.getZone())) {
                // TargetCardInGraveyardBattlefieldOrStack - used for additional payment like Craft, so do not allow big cards for it
                if (!(target instanceof TargetCardInGraveyardBattlefieldOrStack)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasAnyTargets() {
        return !this.any.isEmpty();
    }

    public boolean hasMinNumberOfTargets() {
        return this.target.getMinNumberOfTargets() == 0
                || this.any.size() >= this.target.getMinNumberOfTargets();
    }
}