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
package mage.sets.phyrexiavsthecoalition;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author FenrisulfrX
 */
public class PhyrexianTotem extends CardImpl {

    public PhyrexianTotem(UUID ownerId) {
        super(ownerId, 20, "Phyrexian Totem", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "DDE";

        // {tap}: Add {B} to your mana pool.
        this.addAbility(new BlackManaAbility());
        // {2}{B}: {this} becomes a 5/5 black Horror artifact creature with trample until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(
                new PhyrexianTotemToken(), "", Duration.EndOfTurn), new ManaCostsImpl("{2}{B}")));
        // Whenever {this} is dealt damage, if it's a creature, sacrifice that many permanents.
        this.addAbility(new PhyrexianTotemTriggeredAbility());
    }

    public PhyrexianTotem(final PhyrexianTotem card) {
        super(card);
    }

    @Override
    public PhyrexianTotem copy() {
        return new PhyrexianTotem(this);
    }
    
    private class PhyrexianTotemToken extends Token {
        PhyrexianTotemToken() {
            super("", "a 5/5 black Horror artifact creature with trample");
            cardType.add(CardType.ARTIFACT);
            cardType.add(CardType.CREATURE);
            color.setBlack(true);
            this.subtype.add("Horror");
            power = new MageInt(5);
            toughness = new MageInt(5);
            this.addAbility(TrampleAbility.getInstance());
        }
    }
}

class PhyrexianTotemTriggeredAbility extends TriggeredAbilityImpl {
    
    public PhyrexianTotemTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeEffect(new FilterControlledPermanent(), 0,""));
    }
    
    public PhyrexianTotemTriggeredAbility(final PhyrexianTotemTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public PhyrexianTotemTriggeredAbility copy() {
        return new PhyrexianTotemTriggeredAbility(this);
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if(event.getType() == GameEvent.EventType.DAMAGED_CREATURE && event.getTargetId().equals(this.sourceId)) {
            UUID controller = game.getControllerId(event.getTargetId());
            if(controller != null) {
                Player player = game.getPlayer(controller);
                Card card = game.getCard(event.getSourceId());
                if(player != null && card.getCardType().contains(CardType.CREATURE)) {
                    getEffects().get(0).setTargetPointer(new FixedTarget(player.getId()));
                    ((SacrificeEffect) getEffects().get(0)).setAmount(new StaticValue(event.getAmount()));
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public String getRule() {
        return "Whenever {this} is dealt damage, if it's a creature, sacrifice that many permanents.";
    }
}