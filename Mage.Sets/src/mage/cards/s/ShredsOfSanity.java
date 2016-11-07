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
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author LevelX2
 */
public class ShredsOfSanity extends CardImpl {

    private final static FilterCard filterInstant = new FilterCard("an instant card in your graveyard");
    private final static FilterCard filterSorcery = new FilterCard("a sorcery card in your graveyard");

    static {
        filterInstant.add(new CardTypePredicate(CardType.INSTANT));
        filterSorcery.add(new CardTypePredicate(CardType.SORCERY));
    }

    public ShredsOfSanity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}");

        // Return up to one target instant card and up to one target sorcery card from your graveyard to your hand, then discard a card. Exile Shreds of Sanity.
        this.getSpellAbility().addEffect(new ShredsOfSanityEffect());
        this.getSpellAbility().addTarget(new TargetCard(0, 1, Zone.GRAVEYARD, filterInstant));
        this.getSpellAbility().addTarget(new TargetCard(0, 1, Zone.GRAVEYARD, filterSorcery));
        this.getSpellAbility().addEffect(new ExileSourceEffect());
    }

    public ShredsOfSanity(final ShredsOfSanity card) {
        super(card);
    }

    @Override
    public ShredsOfSanity copy() {
        return new ShredsOfSanity(this);
    }
}

class ShredsOfSanityEffect extends OneShotEffect {

    public ShredsOfSanityEffect() {
        super(Outcome.Benefit);
        this.staticText = "return up to one target instant card and up to one target sorcery card from your graveyard to your hand, then discard a card";
    }

    public ShredsOfSanityEffect(final ShredsOfSanityEffect effect) {
        super(effect);
    }

    @Override
    public ShredsOfSanityEffect copy() {
        return new ShredsOfSanityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cardsToHand = new CardsImpl();
            Card card = game.getCard(getTargetPointer().getFirst(game, source));
            if (card != null) {
                cardsToHand.add(card);
            }
            card = game.getCard(source.getTargets().get(1).getFirstTarget());
            if (card != null) {
                cardsToHand.add(card);
            }
            controller.moveCards(cardsToHand, Zone.HAND, source, game);
            controller.discardOne(false, source, game);
            return true;
        }
        return false;
    }
}
