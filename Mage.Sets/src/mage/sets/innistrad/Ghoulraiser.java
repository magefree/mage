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
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.util.RandomUtil;

/**
 *
 * @author North
 */
public class Ghoulraiser extends CardImpl {

    public Ghoulraiser(UUID ownerId) {
        super(ownerId, 102, "Ghoulraiser", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Zombie");

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Ghoulraiser enters the battlefield, return a Zombie card at random from your graveyard to your hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GhoulraiserEffect(), false));
    }

    public Ghoulraiser(final Ghoulraiser card) {
        super(card);
    }

    @Override
    public Ghoulraiser copy() {
        return new Ghoulraiser(this);
    }
}

class GhoulraiserEffect extends OneShotEffect {

    public GhoulraiserEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "return a Zombie card at random from your graveyard to your hand";
    }

    public GhoulraiserEffect(final GhoulraiserEffect effect) {
        super(effect);
    }

    @Override
    public GhoulraiserEffect copy() {
        return new GhoulraiserEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            FilterCard filter = new FilterCard("Zombie card");
            filter.add(new SubtypePredicate("Zombie"));
            Card[] cards = player.getGraveyard().getCards(filter, game).toArray(new Card[0]);
            if (cards.length > 0) {
                Card card = cards[RandomUtil.nextInt(cards.length)];
                card.moveToZone(Zone.HAND, source.getSourceId(), game, true);
                game.informPlayers(card.getName() + "returned to the hand of" + player.getLogName());
                return true;
            }
        }
        return false;
    }
}
