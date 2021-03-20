package mage.game.stack;

import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.Zone;
import mage.constants.ZoneDetail;
import mage.filter.predicate.Predicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.util.functions.SpellCopyApplier;

import java.util.UUID;

public interface StackObject extends MageObject, Controllable {

    boolean resolve(Game game);

    UUID getSourceId();

    /**
     * @param source null for fizzled events (sourceId will be null)
     * @param game
     */
    void counter(Ability source, Game game);

    void counter(Ability source, Game game, Zone zone, boolean owner, ZoneDetail zoneDetail);

    Ability getStackAbility();

    boolean chooseNewTargets(Game game, UUID playerId, boolean forceChange, boolean onlyOneTarget, Predicate extraPredicate);

    boolean canTarget(Game game, UUID targetId);

    void createCopyOnStack(Game game, Ability source, UUID newControllerId, boolean chooseNewTargets);

    void createCopyOnStack(Game game, Ability source, UUID newControllerId, boolean chooseNewTargets, int amount);

    void createCopyOnStack(Game game, Ability source, UUID newControllerId, boolean chooseNewTargets, int amount, SpellCopyApplier applier);

    boolean isTargetChanged();

    void setTargetChanged(boolean targetChanged);

    @Override
    StackObject copy();
}
