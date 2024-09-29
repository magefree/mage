package mage.abilities.common;

import mage.abilities.LoyaltyAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.target.targetpointer.FixedTarget;

public class ActivatePlaneswalkerLoyaltyAbilityTriggeredAbility extends TriggeredAbilityImpl {

    private final SubType planeswalkerSubType;
    protected final SetTargetPointer setTargetPointer;

    public ActivatePlaneswalkerLoyaltyAbilityTriggeredAbility(Effect effect, SubType planeswalkerSubType, SetTargetPointer setTargetPointer) {
        super(Zone.BATTLEFIELD, effect, false);
        this.planeswalkerSubType = planeswalkerSubType;
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase("Whenever you activate a loyalty ability of a " + planeswalkerSubType.getDescription() + " planeswalker, ");
    }

    private ActivatePlaneswalkerLoyaltyAbilityTriggeredAbility(final ActivatePlaneswalkerLoyaltyAbilityTriggeredAbility ability) {
        super(ability);
        this.planeswalkerSubType = ability.planeswalkerSubType;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public ActivatePlaneswalkerLoyaltyAbilityTriggeredAbility copy() {
        return new ActivatePlaneswalkerLoyaltyAbilityTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getPlayerId().equals(getControllerId())) {
            return false;
        }
        StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getSourceId());
        if (stackAbility == null || !(stackAbility.getStackAbility() instanceof LoyaltyAbility)) {
            return false;
        }
        Permanent permanent = stackAbility.getSourcePermanentOrLKI(game);
        if (permanent == null || !permanent.isPlaneswalker(game)
                || !permanent.hasSubtype(planeswalkerSubType, game)) {
            return false;
        }

        switch (setTargetPointer) {
            case NONE:
                break;
            case PLAYER:
                getAllEffects().setTargetPointer(new FixedTarget(getControllerId(), game));
                break;
            case SPELL:
                getAllEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
                break;
            case PERMANENT:
                getAllEffects().setTargetPointer(new FixedTarget(event.getSourceId(), game));
                break;
            default:
                throw new UnsupportedOperationException("Unexpected setTargetPointer in ActivatePlaneswalkerLoyaltyAbilityTriggeredAbility: " + setTargetPointer);
        }
        return true;
    }
}
