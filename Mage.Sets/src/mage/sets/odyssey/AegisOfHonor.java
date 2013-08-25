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



package mage.sets.odyssey;

import mage.constants.CardType;
import mage.target.TargetSource;
import mage.abilities.effects.RedirectionEffect;
import mage.constants.Rarity;
import mage.cards.CardImpl;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.events.GameEvent.EventType;
import mage.game.events.GameEvent;
import mage.costs.mana.ManaCostImpl;
import mage.abilities.common.SimpleActivatedAbility;

import java.util.UUID;


public class AegisOfHonor extends CardImpl<AegisOfHonor>{
	
	public AegisOfHonor(ownerId UUID){
	super(ownerId, 1, "Aegis of Honor", Rarity.RARE, new CardType[]{Cardtype.ENCHANTMENT}, "{W}");
	this.ExpansionSetCode = "ODY";
	
	this.color.setWhite(true);
	
	// {1} The next time The next time an instant or sorcery spell would deal damage to you this 
	//turn, that spell deals that damage to its controller instead.
	this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AegisOfHonorEffect(), ManaCostsImpl("1")));
	
	
	}
	public AegisOfHonor(final AegisOfHonor card) {
        super(card);
    }

    @Override
    public AegisOfHonor copy() {
        return new AegisOfHonor(this);
	
}

}
    class AegisOfHonorEffect extends RedirectionEffect<AegisOfHonorEffect> {
    

    
    public AegisOfHonorEffect() {
        super(Duration.EndOfTurn);
        staticText = "The next time The next time an instant or sorcery spell would deal "
        		+ "damage to you this turn, that spell deals that damage to its controller "
        		+ "instead.";
    } 
  

    @Override
    public AegisOfHonorEffect copy() {
        return new AegisOfHonorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
    	if (event.getType().equals(GameEvent.EventType.DAMAGE_PLAYER) //Checks for player damage
			&& event.getTargetID().equals(source.getControllerID()) //Checks to see the damage is to Aven Shrine's controller
			{
				Card sourceCard = game.getCard(event.getSourceID());
				if (sourceCard != null && (sourceCard.getCardType().contains(CardType.INSTANT) || sourceCard.getCardType().containts(CardType.SORCERY))); {
				             //Checks if damage is from a sorcery or instants
				  return true;              
				}
    		return true;
    	}
    	return false;
    }
    
 }
