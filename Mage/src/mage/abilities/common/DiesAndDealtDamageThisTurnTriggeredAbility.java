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

public class DiesAndDealtDamageThisTurnTriggeredAbility extends TriggeredAbilityImpl {

    public DiesAndDealtDamageThisTurnTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public DiesAndDealtDamageThisTurnTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.ALL, effect, optional);
    }

    public DiesAndDealtDamageThisTurnTriggeredAbility(final DiesAndDealtDamageThisTurnTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DiesAndDealtDamageThisTurnTriggeredAbility copy() {
        return new DiesAndDealtDamageThisTurnTriggeredAbility(this);
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
                    if (mor.refersTo(getSourceObject(game))) {
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