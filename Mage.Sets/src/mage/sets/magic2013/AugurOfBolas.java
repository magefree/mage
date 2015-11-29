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
package mage.sets.magic2013;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;

/**
 *
 * @author jeffwadsworth
 */
public class AugurOfBolas extends CardImpl {

    private static final FilterCard filter = new FilterCard("an instant or sorcery card");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.INSTANT), new CardTypePredicate(CardType.SORCERY)));
    }

    public AugurOfBolas(UUID ownerId) {
        super(ownerId, 43, "Augur of Bolas", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "M13";
        this.subtype.add("Merfolk");
        this.subtype.add("Wizard");

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When Augur of Bolas enters the battlefield, look at the top three cards of your library. You may reveal an instant or sorcery card from among them and put it into your hand. Put the rest on the bottom of your library in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AugurOfBolasEffect()));
    }

    public AugurOfBolas(final AugurOfBolas card) {
        super(card);
    }

    @Override
    public AugurOfBolas copy() {
        return new AugurOfBolas(this);
    }
}

class AugurOfBolasEffect extends OneShotEffect {

    public AugurOfBolasEffect() {
        super(Outcome.DrawCard);
        this.staticText = "look at the top three cards of your library. You may reveal an instant or sorcery card from among them and put it into your hand. Put the rest on the bottom of your library in any order";
    }

    public AugurOfBolasEffect(final AugurOfBolasEffect effect) {
        super(effect);
    }

    @Override
    public AugurOfBolasEffect copy() {
        return new AugurOfBolasEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            Cards topCards = new CardsImpl();
            topCards.addAll(controller.getLibrary().getTopCards(game, 3));
            if (!topCards.isEmpty()) {
                controller.lookAtCards(sourceObject.getIdName(), topCards, game);
                int number = topCards.count(new FilterInstantOrSorceryCard(), source.getSourceId(), source.getControllerId(), game);
                if (number > 0) {
                    if (controller.chooseUse(outcome, "Reveal an instant or sorcery card from the looked at cards and put it into your hand?", source, game)) {
                        Card card = null;
                        if (number == 1) {
                            card = topCards.getCards(new FilterInstantOrSorceryCard(), source.getSourceId(), source.getControllerId(), game).iterator().next();
                        } else {
                            Target target = new TargetCard(Zone.LIBRARY, new FilterInstantOrSorceryCard());
                            controller.chooseTarget(outcome, target, source, game);
                            card = topCards.get(target.getFirstTarget(), game);
                        }
                        if (card != null) {
                            controller.moveCards(card, null, Zone.HAND, source, game);
                            controller.revealCards(sourceObject.getIdName(), new CardsImpl(card), game);
                            topCards.remove(card);
                        }
                    }
                    controller.putCardsOnBottomOfLibrary(topCards, game, source, true);
                }
            }
            return true;
        }
        return false;
    }
}
