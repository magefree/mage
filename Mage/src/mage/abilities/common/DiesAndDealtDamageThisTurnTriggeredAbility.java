package mage.abilities.common;

import mage.Constants;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

public class DiesAndDealtDamageThisTurnTriggeredAbility extends TriggeredAbilityImpl<DiesAndDealtDamageThisTurnTriggeredAbility> {

	public DiesAndDealtDamageThisTurnTriggeredAbility(Effect effect) {
		super(Constants.Zone.ALL, effect);
	}
    
	public DiesAndDealtDamageThisTurnTriggeredAbility(Effect effect, boolean optional) {
		super(Constants.Zone.ALL, effect, true);
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
            Card card = game.getLastKnownInformation(event.getTargetId(), Constants.Zone.BATTLEFIELD);
            if (card instanceof Permanent && ((Permanent)card).getDealtDamageByThisTurn().contains(this.sourceId)) {
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