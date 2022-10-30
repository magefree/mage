package mage.abilities.common;

import mage.abilities.LoyaltyAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;

public class ActivatePlaneswalkerLoyaltyAbilityTriggeredAbility extends TriggeredAbilityImpl {

    private final SubType planeswalkerSubType;

    public ActivatePlaneswalkerLoyaltyAbilityTriggeredAbility(Effect effect, SubType planeswalkerSubType) {
        super(Zone.BATTLEFIELD, effect, false);
        this.planeswalkerSubType = planeswalkerSubType;
        setTriggerPhrase("Whenever you activate a loyalty ability of a " + planeswalkerSubType.getDescription() + " planeswalker, ");
    }

    private ActivatePlaneswalkerLoyaltyAbilityTriggeredAbility(final ActivatePlaneswalkerLoyaltyAbilityTriggeredAbility ability) {
        super(ability);
        this.planeswalkerSubType = ability.planeswalkerSubType;
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
        this.getEffects().setValue("stackObject", stackAbility);
        return true;
    }
}
