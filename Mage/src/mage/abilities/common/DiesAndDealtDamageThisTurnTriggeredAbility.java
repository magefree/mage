package mage.abilities.common;

import mage.Constants;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

public class DiesAndDealtDamageThisTurnTriggeredAbility extends TriggeredAbilityImpl<DiesAndDealtDamageThisTurnTriggeredAbility> {

	public DiesAndDealtDamageThisTurnTriggeredAbility(Effect effect) {
		super(Constants.Zone.BATTLEFIELD, effect);
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
		if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
			ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
			if (zEvent.getFromZone() == Constants.Zone.BATTLEFIELD && zEvent.getToZone() == Constants.Zone.GRAVEYARD) {
				Permanent p = (Permanent) game.getLastKnownInformation(event.getTargetId(), Constants.Zone.BATTLEFIELD);
				if (p.getDealtDamageByThisTurn().contains(this.sourceId)) {
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