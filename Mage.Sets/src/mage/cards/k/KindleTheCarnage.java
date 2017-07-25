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
package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class KindleTheCarnage extends CardImpl {

    public KindleTheCarnage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}{R}");

        // Discard a card at random. If you do, Kindle the Carnage deals damage equal to that card's converted mana cost to each creature. You may repeat this process any number of times.
        this.getSpellAbility().addEffect(new KindleTheCarnageEffect());

    }

    public KindleTheCarnage(final KindleTheCarnage card) {
        super(card);
    }

    @Override
    public KindleTheCarnage copy() {
        return new KindleTheCarnage(this);
    }
}

class KindleTheCarnageEffect extends OneShotEffect {

    public KindleTheCarnageEffect() {
        super(Outcome.AIDontUseIt);
        this.staticText = "Discard a card at random. If you do, {this} deals damage equal to that card's converted mana cost to each creature. You may repeat this process any number of times";
    }

    public KindleTheCarnageEffect(final KindleTheCarnageEffect effect) {
        super(effect);
    }

    @Override
    public KindleTheCarnageEffect copy() {
        return new KindleTheCarnageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards hand = controller.getHand();
            while (hand != null
                    && hand.size() > 0
                    && controller.isInGame()
                    && controller.chooseUse(Outcome.AIDontUseIt, "Discard a card randomly from your hand?", source, game)) {
                Card discardedCard = controller.discardOne(true, source, game);
                if (discardedCard != null) {
                    new DamageAllEffect(discardedCard.getConvertedManaCost(), new FilterCreaturePermanent()).apply(game, source);
                }
            }
            return true;
        }
        return false;
    }
}
