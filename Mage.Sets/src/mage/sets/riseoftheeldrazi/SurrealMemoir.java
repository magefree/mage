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
package mage.sets.riseoftheeldrazi;

import java.util.Random;
import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.util.RandomUtil;

/**
 *
 * @author North
 */
public class SurrealMemoir extends CardImpl {

    public SurrealMemoir(UUID ownerId) {
        super(ownerId, 167, "Surreal Memoir", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{3}{R}");
        this.expansionSetCode = "ROE";


        // Return an instant card at random from your graveyard to your hand.
        this.getSpellAbility().addEffect(new SurrealMemoirEffect());
        this.addAbility(new ReboundAbility());
    }

    public SurrealMemoir(final SurrealMemoir card) {
        super(card);
    }

    @Override
    public SurrealMemoir copy() {
        return new SurrealMemoir(this);
    }
}

class SurrealMemoirEffect extends OneShotEffect {

    public SurrealMemoirEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return an instant card at random from your graveyard to your hand";
    }

    public SurrealMemoirEffect(final SurrealMemoirEffect effect) {
        super(effect);
    }

    @Override
    public SurrealMemoirEffect copy() {
        return new SurrealMemoirEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            FilterCard filter = new FilterCard("instant card");
            filter.add(new CardTypePredicate(CardType.INSTANT));
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
