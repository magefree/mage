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
package mage.sets.apocalypse;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
/**
 *
 * @author fireshoes
 */
public class KavuHowler extends CardImpl {

    public KavuHowler(UUID ownerId) {
        super(ownerId, 79, "Kavu Howler", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.expansionSetCode = "APC";
        this.subtype.add("Kavu");

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // When Kavu Howler enters the battlefield, reveal the top four cards of your library. Put all Kavu cards revealed this way into your hand and the rest on the bottom of your library in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new KavuHowlerEffect()));

    }

    public KavuHowler(final KavuHowler card) {
        super(card);
    }

    @Override
    public KavuHowler copy() {
        return new KavuHowler(this);
    }
}

class KavuHowlerEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("Kavu");
    static {
        filter.add(new SubtypePredicate("Kavu"));
    }

    public KavuHowlerEffect() {
        super(Outcome.DrawCard);
        this.staticText = "reveal the top four cards of your library. Put all Kavu cards revealed this way into your hand and the rest on the bottom of your library in any order";
    }

    public KavuHowlerEffect(final KavuHowlerEffect effect) {
        super(effect);
    }

    @Override
    public KavuHowlerEffect copy() {
        return new KavuHowlerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller == null || sourceObject == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        cards.addAll(controller.getLibrary().getTopCards(game, 4));
        controller.revealCards(sourceObject.getName(), cards, game);
        for (Card card: cards.getCards(game)) {
            if (filter.match(card, game)) {
                controller.moveCardToHandWithInfo(card, source.getSourceId(), game, Zone.LIBRARY);
                cards.remove(card);
            }
        }
        controller.putCardsOnBottomOfLibrary(cards, game, source, true);
        return true;
    }
}