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
package mage.sets.innistrad;

import java.util.Random;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public class WoodlandSleuth extends CardImpl<WoodlandSleuth> {

    private static final String staticText = "Morbid - When {this} enters the battlefield, if a creature died this turn, return a creature card at random from your graveyard to your hand.";

    public WoodlandSleuth(UUID ownerId) {
        super(ownerId, 210, "Woodland Sleuth", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Human");
        this.subtype.add("Scout");

        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Morbid - When Woodland Sleuth enters the battlefield, if a creature died this turn, return a creature card at random from your graveyard to your hand.
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new WoodlandSleuthEffect());
        this.addAbility(new ConditionalTriggeredAbility(ability, MorbidCondition.getInstance(), staticText));
    }

    public WoodlandSleuth(final WoodlandSleuth card) {
        super(card);
    }

    @Override
    public WoodlandSleuth copy() {
        return new WoodlandSleuth(this);
    }
}

class WoodlandSleuthEffect extends OneShotEffect<WoodlandSleuthEffect> {

    public WoodlandSleuthEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "return a creature card at random from your graveyard to your hand";
    }

    public WoodlandSleuthEffect(final WoodlandSleuthEffect effect) {
        super(effect);
    }

    @Override
    public WoodlandSleuthEffect copy() {
        return new WoodlandSleuthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Card[] cards = player.getGraveyard().getCards(new FilterCreatureCard(), game).toArray(new Card[0]);
            if (cards.length > 0) {
                Random rnd = new Random();
                Card card = cards[rnd.nextInt(cards.length)];
                card.moveToZone(Zone.HAND, source.getId(), game, true);
                game.informPlayers(card.getName() + " returned to the hand of " + player.getName());
                return true;
            }
        }
        return false;
    }
}
