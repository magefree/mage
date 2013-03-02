/*
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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;

/**
 *
 * @author Plopman
 */
public class LeylinePhantom extends CardImpl<LeylinePhantom> {

    public LeylinePhantom(UUID ownerId) {
        super(ownerId, 41, "Leyline Phantom", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{4}{U}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Illusion");

        this.color.setBlue(true);
        
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        //Whenever Leyline Phantom deals combat damage, return it to its owner's hand.
        this.addAbility(new LeylinePhantomTriggeredAbility());
    }

    public LeylinePhantom(final LeylinePhantom card) {
        super(card);
    }

    @Override
    public LeylinePhantom copy() {
        return new LeylinePhantom(this);
    }
}


class LeylinePhantomTriggeredAbility extends TriggeredAbilityImpl<LeylinePhantomTriggeredAbility> {


    public LeylinePhantomTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new ReturnToHandSourceEffect(true), false);
    }

    public LeylinePhantomTriggeredAbility(final LeylinePhantomTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LeylinePhantomTriggeredAbility copy() {
        return new LeylinePhantomTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_CREATURE || event.getType() == GameEvent.EventType.DAMAGED_PLANESWALKER || event.getType() == GameEvent.EventType.DAMAGED_PLAYER) {
            if(((DamagedEvent) event).isCombatDamage() && event.getSourceId().equals(this.getSourceId())){
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage, " + super.getRule();
    }

}
