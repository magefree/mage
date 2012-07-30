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
package mage.sets.ravnika;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author jeffwadsworth
 */
public class Mindmoil extends CardImpl<Mindmoil> {

    public Mindmoil(UUID ownerId) {
        super(ownerId, 135, "Mindmoil", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{4}{R}");
        this.expansionSetCode = "RAV";

        this.color.setRed(true);

        // Whenever you cast a spell, put the cards in your hand on the bottom of your library in any order, then draw that many cards.
        this.addAbility(new SpellCastTriggeredAbility(new MindmoilEffect(), false));
    }

    public Mindmoil(final Mindmoil card) {
        super(card);
    }

    @Override
    public Mindmoil copy() {
        return new Mindmoil(this);
    }
}

class MindmoilEffect extends OneShotEffect<MindmoilEffect> {

    public MindmoilEffect() {
        super(Constants.Outcome.Neutral);
        staticText = "put the cards in your hand on the bottom of your library in any order, then draw that many cards";
    }

    public MindmoilEffect(final MindmoilEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        if (you != null) {
            int count = you.getHand().size();
            Cards cards = new CardsImpl();
            for (Card card : you.getHand().getCards(game)) {
                cards.add(card.getId());
            }
            TargetCard target = new TargetCard(Constants.Zone.PICK, new FilterCard("card to put on the bottom of your library"));
            target.setRequired(true);
            while (cards.size() > 1) {
                you.choose(Constants.Outcome.Neutral, cards, target, game);
                Card card = cards.get(target.getFirstTarget(), game);
                if (card != null) {
                    cards.remove(card);
                    card.moveToZone(Constants.Zone.LIBRARY, source.getId(), game, false);
                }
                target.clearChosen();
            }
            if (cards.size() == 1) {
                Card card = cards.get(cards.iterator().next(), game);
                card.moveToZone(Constants.Zone.LIBRARY, source.getId(), game, false);
            }
            you.getHand().clear();
            you.drawCards(count, game);
        }
        return true;
    }

    @Override
    public MindmoilEffect copy() {
        return new MindmoilEffect(this);
    }
}