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
package mage.sets.eldritchmoon;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.SkulkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.token.Token;

/**
 *
 * @author fireshoes
 */
public class WharfInfiltrator extends CardImpl {

    public WharfInfiltrator(UUID ownerId) {
        super(ownerId, 80, "Wharf Infiltrator", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "EMN";
        this.subtype.add("Human");
        this.subtype.add("Horror");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Skulk
        this.addAbility(new SkulkAbility());

        // Whenever Wharf Infiltrator deals combat damage to a player, you may draw a card. If you do, discard a card.
        Effect effect = new DrawDiscardControllerEffect();
        effect.setText("you may draw a card. If you do, discard a card");
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(effect, true));

        // Whenever you discard a creature card, you may pay {2}. If you do, put a 3/2 colorless Eldrazi Horror creature token onto the battlefield.
        effect = new CreateTokenEffect(new EldraziHorrorToken());
        effect.setText("put a 3/2 colorless Eldrazi Horror creature token onto the battlefield");
        DoIfCostPaid doIfCostPaid = new DoIfCostPaid(effect, new GenericManaCost(2));
        this.addAbility(new WharfInfiltratorDiscardAbility(doIfCostPaid));
    }

    public WharfInfiltrator(final WharfInfiltrator card) {
        super(card);
    }

    @Override
    public WharfInfiltrator copy() {
        return new WharfInfiltrator(this);
    }
}

class WharfInfiltratorDiscardAbility extends TriggeredAbilityImpl {

    WharfInfiltratorDiscardAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
    }

    WharfInfiltratorDiscardAbility(final WharfInfiltratorDiscardAbility ability) {
        super(ability);
    }

    @Override
    public WharfInfiltratorDiscardAbility copy() {
        return new WharfInfiltratorDiscardAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DISCARDED_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Card card = game.getCard(event.getTargetId());
        if (getControllerId().equals(event.getPlayerId()) && card != null && card.getCardType().contains(CardType.CREATURE)) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you discard a creature card, " + super.getRule();
    }
}

class EldraziHorrorToken extends Token {

    public EldraziHorrorToken() {
        super("Eldrazi Horror", "3/2 colorless Eldrazi Horror creature token");
        cardType.add(CardType.CREATURE);
        subtype.add("Eldrazi");
        subtype.add("Horror");
        power = new MageInt(3);
        toughness = new MageInt(2);
    }
}
