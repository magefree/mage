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
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.filter.Filter.ComparisonScope;
import mage.filter.Filter.ComparisonType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SunTitan extends CardImpl<SunTitan> {

	public SunTitan(UUID ownerId) {
		super(ownerId, 35, "Sun Titan", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");
		this.expansionSetCode = "M11";
		this.subtype.add("Giant");
		this.color.setWhite(true);
		this.power = new MageInt(6);
		this.toughness = new MageInt(6);

		this.addAbility(VigilanceAbility.getInstance());
		this.addAbility(new SunTitanAbility());
	}

	public SunTitan(final SunTitan card) {
		super(card);
	}

	@Override
	public SunTitan copy() {
		return new SunTitan(this);
	}

	@Override
	public String getArt() {
		return "129119_typ_reg_sty_010.jpg";
	}

}

class SunTitanAbility extends TriggeredAbilityImpl<SunTitanAbility> {

	private static FilterCard filter = new FilterCard("permanent card with converted mana cost 3 or less");

	static {
		filter.getCardType().add(CardType.ARTIFACT);
		filter.getCardType().add(CardType.CREATURE);
		filter.getCardType().add(CardType.ENCHANTMENT);
		filter.getCardType().add(CardType.LAND);
		filter.getCardType().add(CardType.PLANESWALKER);
		filter.setScopeCardType(ComparisonScope.Any);
		filter.setConvertedManaCost(4);
		filter.setConvertedManaCostComparison(ComparisonType.LessThan);
	}

	public SunTitanAbility() {
		super(Zone.BATTLEFIELD, null, true);
		this.addTarget(new TargetCardInYourGraveyard(filter));
		this.addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
	}

	public SunTitanAbility(final SunTitanAbility ability) {
		super(ability);
	}

	@Override
	public SunTitanAbility copy() {
		return new SunTitanAbility(this);
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
		return "Whenever Sun Titan enters the battlefield or attacks, you may return target permanent card with converted mana cost 3 or less from your graveyard to the battlefield.";
	}

}
