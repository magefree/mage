package mage.abilities.common;

import mage.MageObjectReference;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.targetpointer.FixedTarget;

public class DealtDamageAndDiedTriggeredAbility extends TriggeredAbilityImpl {

    public DealtDamageAndDiedTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public DealtDamageAndDiedTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.ALL, effect, optional);
    }

    public DealtDamageAndDiedTriggeredAbility(final DealtDamageAndDiedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DealtDamageAndDiedTriggeredAbility copy() {
        return new DealtDamageAndDiedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((ZoneChangeEvent)event).isDiesEvent()) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getTarget().getCardType().contains(CardType.CREATURE)) {
                boolean damageDealt = false;
                for (MageObjectReference mor : zEvent.getTarget().getDealtDamageByThisTurn()) {
                    if (mor.refersTo(getSourceObject(game), game)) {
                        damageDealt = true;
                        break;
                    }
                }
                if (damageDealt) {
                    for (Effect effect : getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature dealt damage by {this} this turn dies, " + super.getRule();
    }
}