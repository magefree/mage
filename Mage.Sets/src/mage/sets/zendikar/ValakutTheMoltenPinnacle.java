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

package mage.sets.zendikar;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.filter.Filter.ComparisonScope;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author Viserion
 */
public class ValakutTheMoltenPinnacle extends CardImpl<ValakutTheMoltenPinnacle> {
	
	static final FilterLandPermanent filter = new FilterLandPermanent("Mountain");

	static {
	    filter.getSubtype().add("Mountain");
	    filter.setScopeSubtype(ComparisonScope.Any);
	}
	
	public ValakutTheMoltenPinnacle(UUID ownerId) {
		super(ownerId, 228, "Valakut, the Molten Pinnacle", Rarity.RARE, new CardType[]{CardType.LAND}, null);
		this.expansionSetCode = "ZEN";
		this.addAbility(new EntersBattlefieldTappedAbility());
		this.addAbility(new ValakutTheMoltenPinnacleTriggeredAbility());
		this.addAbility(new RedManaAbility());

	}

	public ValakutTheMoltenPinnacle(final ValakutTheMoltenPinnacle card) {
		super(card);
	}

	@Override
	public ValakutTheMoltenPinnacle copy() {
		return new ValakutTheMoltenPinnacle(this);
	}
}

class ValakutTheMoltenPinnacleTriggeredAbility extends TriggeredAbilityImpl<ValakutTheMoltenPinnacleTriggeredAbility> {

	ValakutTheMoltenPinnacleTriggeredAbility () {
		super(Zone.BATTLEFIELD, new DamageTargetEffect(3));
		this.addTarget(new TargetCreatureOrPlayer());
	}

	ValakutTheMoltenPinnacleTriggeredAbility(ValakutTheMoltenPinnacleTriggeredAbility ability) {
		super(ability);
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		
		if (event.getType() == EventType.ZONE_CHANGE && ((ZoneChangeEvent)event).getToZone() == Zone.BATTLEFIELD) {
			Permanent permanent = game.getPermanent(event.getTargetId());
			if (permanent.getCardType().contains(CardType.LAND) && permanent.getControllerId().equals(this.controllerId)) {
				if(permanent.hasSubtype("Mountain")){
					
					int count = game.getBattlefield().count(ValakutTheMoltenPinnacle.filter, permanent.getControllerId(), game);
					
					if(count > 5){
						return true;
					}
				}
			}
		}
		return false;
	}
	@Override
	public ValakutTheMoltenPinnacleTriggeredAbility copy() {
		return new ValakutTheMoltenPinnacleTriggeredAbility(this);
	}
	
	@Override
    public String getRule() {
        return "Whenever a Mountain enters the battlefield under your control, if you control at least five other Mountains, you may have {this} deal 3 damage to target creature or player.";
    }
}