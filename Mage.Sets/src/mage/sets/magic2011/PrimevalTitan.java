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
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.BoostSourceEffect;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.effects.common.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterBasicLandCard;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PrimevalTitan extends CardImpl<PrimevalTitan> {

	public PrimevalTitan(UUID ownerId) {
		super(ownerId, 192, "Primeval Titan", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
		this.expansionSetCode = "M11";
		this.subtype.add("Giant");
		this.color.setGreen(true);
		this.power = new MageInt(6);
		this.toughness = new MageInt(6);

		this.addAbility(TrampleAbility.getInstance());
		this.addAbility(new PrimevalTitanAbility());
	}

	public PrimevalTitan(final PrimevalTitan card) {
		super(card);
	}

	@Override
	public PrimevalTitan copy() {
		return new PrimevalTitan(this);
	}

	@Override
	public String getArt() {
		return "129116_typ_reg_sty_010.jpg";
	}

}

class PrimevalTitanAbility extends TriggeredAbilityImpl<PrimevalTitanAbility> {

	public PrimevalTitanAbility() {
		super(Zone.BATTLEFIELD, null, false);
		TargetCardInLibrary target = new TargetCardInLibrary(2, new FilterLandCard());
		this.addEffect(new SearchLibraryPutInPlayEffect(target, true, Outcome.PutLandInPlay));
	}

	public PrimevalTitanAbility(final PrimevalTitanAbility ability) {
		super(ability);
	}

	@Override
	public PrimevalTitanAbility copy() {
		return new PrimevalTitanAbility(this);
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.getType() == EventType.ATTACKER_DECLARED && event.getSourceId().equals(this.getSourceId())) {
			trigger(game, event.getPlayerId());
			return true;
		}
		if (event.getType() == EventType.ZONE_CHANGE && event.getTargetId().equals(this.getSourceId()) ) {
			ZoneChangeEvent zEvent = (ZoneChangeEvent)event;
			if (zEvent.getToZone() == Zone.BATTLEFIELD) {
				trigger(game, event.getPlayerId());
				return true;
			}
		}
		return false;
	}

	@Override
	public String getRule() {
		return "Whenever Primeval Titan enters the battlefield or attacks, you may search your library for up to two land cards, put them onto the battlefield tapped, then shuffle your library.";
	}

}
