package mage.game.stack;

import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.Zone;
import mage.constants.ZoneDetail;
import mage.filter.FilterPermanent;
import mage.game.Controllable;
import mage.game.Game;

import java.util.UUID;

public interface StackObject extends MageObject, Controllable {

    boolean resolve(Game game);

    UUID getSourceId();

    /**
     *
     * @param source null for fizzled events (sourceId will be null)
     * @param game
     */
    void counter(Ability source, Game game);

    void counter(Ability source, Game game, Zone zone, boolean owner, ZoneDetail zoneDetail);

    Ability getStackAbility();

    boolean chooseNewTargets(Game game, UUID playerId, boolean forceChange, boolean onlyOneTarget, FilterPermanent filterNewTarget);

    StackObject createCopyOnStack(Game game, Ability source, UUID newControllerId, boolean chooseNewTargets);

    StackObject createCopyOnStack(Game game, Ability source, UUID newControllerId, boolean chooseNewTargets, int amount);

    boolean isTargetChanged();

    void setTargetChanged(boolean targetChanged);

    @Override
    StackObject copy();
}
