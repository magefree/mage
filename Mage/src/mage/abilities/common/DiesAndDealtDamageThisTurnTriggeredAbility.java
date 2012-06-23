package mage.abilities.common;

import mage.Constants;
import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

public class DiesAndDealtDamageThisTurnTriggeredAbility extends TriggeredAbilityImpl<DiesAndDealtDamageThisTurnTriggeredAbility> {

    public DiesAndDealtDamageThisTurnTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public DiesAndDealtDamageThisTurnTriggeredAbility(Effect effect, boolean optional) {
        super(Constants.Zone.ALL, effect, optional);
    }

    public DiesAndDealtDamageThisTurnTriggeredAbility(final DiesAndDealtDamageThisTurnTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DiesAndDealtDamageThisTurnTriggeredAbility copy() {
        return new DiesAndDealtDamageThisTurnTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent)event).isDiesEvent()) {
            MageObject object = game.getLastKnownInformation(event.getTargetId(), Constants.Zone.BATTLEFIELD);
            if (object instanceof Permanent && ((Permanent)object).getDealtDamageByThisTurn().contains(this.sourceId)) {
                for (Effect effect : getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature dealt damage by {this} this turn dies, " + super.getRule();
    }
}