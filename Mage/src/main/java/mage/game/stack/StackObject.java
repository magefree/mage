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

    void counter(UUID sourceId, Game game);

    void counter(UUID sourceId, Game game, Zone zone, boolean owner, ZoneDetail zoneDetail);

    Ability getStackAbility();

    //     int getConvertedManaCost();
    boolean chooseNewTargets(Game game, UUID playerId, boolean forceChange, boolean onlyOneTarget, FilterPermanent filterNewTarget);

    StackObject createCopyOnStack(Game game, Ability source, UUID newControllerId, boolean chooseNewTargets);

    StackObject createCopyOnStack(Game game, Ability source, UUID newControllerId, boolean chooseNewTargets, int amount);

    boolean isTargetChanged();

    void setTargetChanged(boolean targetChanged);

    @Override
    StackObject copy();
}
