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
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.abilities.keyword.EvolveAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author Plopman
 */
public class FathomMage extends CardImpl<FathomMage> {

    public FathomMage(UUID ownerId) {
        super(ownerId, 162, "Fathom Mage", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.color.setGreen(true);
        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Evolve (Whenever a creature enters the battlefield under your control, if that creature
        // has greater power or toughness than this creature, put a +1/+1 counter on this creature.)
        this.addAbility(new EvolveAbility());
        
        //Whenever a +1/+1 counter is placed on Fathom Mage, you may draw a card.
        this.addAbility(new FathomMageTriggeredAbility());
    }

    public FathomMage(final FathomMage card) {
        super(card);
    }

    @Override
    public FathomMage copy() {
        return new FathomMage(this);
    }
}


class FathomMageTriggeredAbility extends TriggeredAbilityImpl<FathomMageTriggeredAbility> {

    public FathomMageTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new DrawCardControllerEffect(1), true);
    }

    public FathomMageTriggeredAbility(FathomMageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.COUNTER_ADDED) {
            if (event.getTargetId().equals(this.getSourceId()) && event.getData().equals(CounterType.P1P1.getName())) {
               return true;
            }
        }
        return false;
    }

    @Override
    public FathomMageTriggeredAbility copy() {
        return new FathomMageTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a +1/+1 counter is placed on Fathom Mage, " + super.getRule();
    }
}
