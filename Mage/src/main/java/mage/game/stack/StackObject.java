package mage.game.stack;

import mage.MageItem;
import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.PutCards;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.util.functions.StackObjectCopyApplier;

import java.util.UUID;

public interface StackObject extends MageObject, Controllable {

    boolean resolve(Game game);

    UUID getSourceId();

    /**
     * @param source null for fizzled events (sourceId will be null)
     * @param game
     */
    void counter(Ability source, Game game);

    void counter(Ability source, Game game, PutCards putCard);

    Ability getStackAbility();

    boolean chooseNewTargets(Game game, UUID playerId, boolean forceChange, boolean onlyOneTarget, Predicate<MageItem> newTargetFilterPredicate);

    /**
     * Checks "could target" of the copy effects (see 707.10d, 707.10e): the player or object must be a legal target
     * for each target of the spell or ability, checked for the controller of the spell or ability.
     * <p>
     * It's not the targets check on resolve - that check is done by the targets themselves.
     *
     * @param game
     * @param targetId player or object to check
     * @return false for a spell or ability without targets
     */
    boolean canTarget(Game game, UUID targetId);

    void createCopyOnStack(Game game, Ability source, UUID newControllerId, boolean chooseNewTargets);

    void createCopyOnStack(Game game, Ability source, UUID newControllerId, boolean chooseNewTargets, int amount);

    void createCopyOnStack(Game game, Ability source, UUID newControllerId, boolean chooseNewTargets, int amount, StackObjectCopyApplier applier);

    void createSingleCopy(UUID newControllerId, StackObjectCopyApplier applier, MageObjectReferencePredicate newTargetFilterPredicate, Game game, Ability source, boolean chooseNewTargets);

    boolean canBeCopied();

    boolean isTargetChanged();

    void setTargetChanged(boolean targetChanged);

    @Override
    StackObject copy();
}
