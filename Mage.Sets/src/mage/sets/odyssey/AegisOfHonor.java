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

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.RedirectionEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.target.TargetPermanent;

/**
 * @author cbt33 / LevelX2
 */

public class AegisOfHonor extends CardImpl<AegisOfHonor>{

	public AegisOfHonor(UUID ownerId){
	super(ownerId, 1, "Aegis of Honor", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{W}");
	this.expansionSetCode = "ODY";

	this.color.setWhite(true);

	// {1}: The next time an instant or sorcery spell would deal damage to you this
	//turn, that spell deals that damage to its controller instead.
	this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AegisOfHonorEffect(), new ManaCostsImpl("{1}")));


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

    private static final FilterInstantOrSorceryCard instantOrSorceryfilter = new FilterInstantOrSorceryCard();

    public AegisOfHonorEffect() {
        super(Duration.EndOfTurn);
        staticText = "The next time an instant or sorcery spell would deal "
        		+ "damage to you this turn, that spell deals that damage to its controller instead";
    }

    public AegisOfHonorEffect(final AegisOfHonorEffect card) {
        super(card);
    }


    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
    	if (event.getType().equals(GameEvent.EventType.DAMAGE_PLAYER)   //Checks for player damage
			&& event.getTargetId().equals(source.getControllerId()))    //Checks to see the damage is to Aegis of Honor's controller
        {
            Spell spell = null;
            StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
            if (stackObject == null) {
               stackObject = (StackObject) game.getLastKnownInformation(event.getSourceId(), Zone.STACK);
            }
            if (stackObject instanceof Spell) {
                spell = (Spell) stackObject;
            }
            //Checks if damage is from a sorcery or instants
            if (spell != null && instantOrSorceryfilter.match(spell.getCard(), game)) {
                TargetPermanent target = new TargetPermanent();
                target.add(spell.getControllerId(), game);
                redirectTarget = target;
                return true;
            }
    	}
    	return false;
    }

    @Override
    public AegisOfHonorEffect copy() {
        return new AegisOfHonorEffect(this);
    }

 }
