package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.SpecialAction;
import mage.abilities.condition.common.RoomHalfLockedCondition;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author oscscull
 *         Special action for Room cards to unlock a locked half by paying its
 *         mana
 *         cost.
 *         This ability is only present if the corresponding half is currently
 *         locked.
 */
public class RoomUnlockAbility extends SpecialAction {

    private final boolean isLeftHalf;

    public RoomUnlockAbility(ManaCosts costs, boolean isLeftHalf) {
        super(Zone.BATTLEFIELD, null);
        this.addCost(costs);

        this.isLeftHalf = isLeftHalf;
        this.timing = TimingRule.SORCERY;

        // only works if the relevant half is *locked*
        if (isLeftHalf) {
            this.setCondition(RoomHalfLockedCondition.LEFT);
        } else {
            this.setCondition(RoomHalfLockedCondition.RIGHT);
        }

        // Adds the effect to pay + unlock the half
        this.addEffect(new RoomUnlockHalfEffect(isLeftHalf));
    }

    protected RoomUnlockAbility(final RoomUnlockAbility ability) {
        super(ability);
        this.isLeftHalf = ability.isLeftHalf;
    }

    @Override
    public RoomUnlockAbility copy() {
        return new RoomUnlockAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder();
        sb.append(getManaCostsToPay().getText()).append(": ");
        sb.append("Unlock the ");
        sb.append(isLeftHalf ? "left" : "right").append(" half.");
        sb.append(" <i>(Activate only as a sorcery, and only if the ");
        sb.append(isLeftHalf ? "left" : "right").append(" half is locked.)</i>");
        return sb.toString();
    }
}

/**
 * Allows you to pay to unlock the door
 */
class RoomUnlockHalfEffect extends OneShotEffect {

    private final boolean isLeftHalf;

    public RoomUnlockHalfEffect(boolean isLeftHalf) {
        super(Outcome.Neutral);
        this.isLeftHalf = isLeftHalf;
        staticText = "unlock the " + (isLeftHalf ? "left" : "right") + " half";
    }

    private RoomUnlockHalfEffect(final RoomUnlockHalfEffect effect) {
        super(effect);
        this.isLeftHalf = effect.isLeftHalf;
    }

    @Override
    public RoomUnlockHalfEffect copy() {
        return new RoomUnlockHalfEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);

        if (permanent == null) {
            return false;
        }

        if (isLeftHalf && permanent.isLeftDoorUnlocked()) {
            return false;
        }
        if (!isLeftHalf && permanent.isRightDoorUnlocked()) {
            return false;
        }

        return permanent.unlockDoor(game, source, isLeftHalf);
    }
}