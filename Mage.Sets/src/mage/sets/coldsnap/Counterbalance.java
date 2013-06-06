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
package mage.sets.coldsnap;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.OpponentCastsSpellTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class Counterbalance extends CardImpl<Counterbalance> {

    public Counterbalance(UUID ownerId) {
        super(ownerId, 31, "Counterbalance", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{U}{U}");
        this.expansionSetCode = "CSP";

        this.color.setBlue(true);

        // Whenever an opponent casts a spell, you may reveal the top card of your library. If you do, counter that spell if it has the same converted mana cost as the revealed card.
        this.addAbility(new OpponentCastsSpellTriggeredAbility(new CounterbalanceEffect(), true));
    }

    public Counterbalance(final Counterbalance card) {
        super(card);
    }

    @Override
    public Counterbalance copy() {
        return new Counterbalance(this);
    }
}

class CounterbalanceEffect extends OneShotEffect<CounterbalanceEffect> {

    public CounterbalanceEffect() {
        super(Outcome.Neutral);
        this.staticText = "you may reveal the top card of your library. If you do, counter that spell if it has the same converted mana cost as the revealed card";
    }

    public CounterbalanceEffect(final CounterbalanceEffect effect) {
        super(effect);
    }

    @Override
    public CounterbalanceEffect copy() {
        return new CounterbalanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        if (you == null) {
            return false;
        }
        Card topcard = you.getLibrary().getFromTop(game);
        if (topcard == null) {
            return false;
        }
        CardsImpl cards = new CardsImpl();
        cards.add(topcard);
        you.revealCards("Counterbalance", cards, game);
        int cmc = topcard.getManaCost().convertedManaCost();
        StackObject spell = game.getStack().getStackObject(targetPointer.getFirst(game, source));
        if (spell == null) {
            return false;
        }
        if (cmc == spell.getManaCost().convertedManaCost()) {
            return game.getStack().counter(spell.getSourceId(), source.getSourceId(), game);
        }
        return false;
    }
}
