/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 * 
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 * 
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 * 
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 * 
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */

package mage.sets.magic2011;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.SkipNextUntapTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.target.TargetPermanent;
import mage.target.TargetStackObject;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class FrostTitan extends CardImpl<FrostTitan> {

	public FrostTitan(UUID ownerId) {
		super(ownerId, 55, "Frost Titan", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");
		this.expansionSetCode = "M11";
		this.subtype.add("Giant");
		this.color.setBlue(true);
		this.power = new MageInt(6);
		this.toughness = new MageInt(6);

		this.addAbility(new FrostTitanAbility1());
		this.addAbility(new FrostTitanAbility2());
	}

	public FrostTitan(final FrostTitan card) {
		super(card);
	}

	@Override
	public FrostTitan copy() {
		return new FrostTitan(this);
	}

}

class FrostTitanAbility1 extends TriggeredAbilityImpl<FrostTitanAbility1> {

	public FrostTitanAbility1() {
		super(Zone.BATTLEFIELD, new CounterUnlessPaysEffect(new GenericManaCost(2)), false);
	}

	public FrostTitanAbility1(final FrostTitanAbility1 ability) {
		super(ability);
	}

	@Override
	public FrostTitanAbility1 copy() {
		return new FrostTitanAbility1(this);
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.getType() == EventType.TARGETED && event.getTargetId().equals(this.getSourceId()) && game.getOpponents(this.controllerId).contains(event.getPlayerId())) {
			this.getTargets().clear();
			TargetStackObject target = new TargetStackObject();
			target.add(event.getSourceId(), game);
			this.addTarget(target);
			return true;
		}
		return false;
	}

	@Override
	public String getRule() {
		return "Whenever {this} becomes the target of a spell or ability an opponent controls, counter that spell or ability unless its controller pays {2}";
	}

}

class FrostTitanAbility2 extends TriggeredAbilityImpl<FrostTitanAbility2> {

	public FrostTitanAbility2() {
		super(Zone.BATTLEFIELD, new TapTargetEffect(), false);
		this.addEffect(new SkipNextUntapTargetEffect());
		this.addTarget(new TargetPermanent());
	}

	public FrostTitanAbility2(final FrostTitanAbility2 ability) {
		super(ability);
	}

	@Override
	public FrostTitanAbility2 copy() {
		return new FrostTitanAbility2(this);
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.getType() == EventType.ATTACKER_DECLARED && event.getSourceId().equals(this.getSourceId())) {
			return true;
		}
		if (event.getType() == EventType.ZONE_CHANGE && event.getTargetId().equals(this.getSourceId()) ) {
			ZoneChangeEvent zEvent = (ZoneChangeEvent)event;
			if (zEvent.getToZone() == Zone.BATTLEFIELD) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getRule() {
		return "Whenever {this} enters the battlefield or attacks, tap target permanent. It doesn't untap during its controller's next untap step";
	}

}
