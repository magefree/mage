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
import mage.abilities.keyword.HasteAbility;
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
 * @author Plopman
 */
public class GoblinRingleader extends CardImpl {

    public GoblinRingleader(UUID ownerId) {
        super(ownerId, 62, "Goblin Ringleader", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.expansionSetCode = "APC";
        this.subtype.add("Goblin");

        this.color.setRed(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // When Goblin Ringleader enters the battlefield, reveal the top four cards of your library. Put all Goblin cards revealed this way into your hand and the rest on the bottom of your library in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GoblinRingleaderEffect()));
    }

    public GoblinRingleader(final GoblinRingleader card) {
        super(card);
    }

    @Override
    public GoblinRingleader copy() {
        return new GoblinRingleader(this);
    }
}

class GoblinRingleaderEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("Goblin");
    static {
        filter.add(new SubtypePredicate("Goblin"));
    }
    
    public GoblinRingleaderEffect() {
        super(Outcome.DrawCard);
        this.staticText = "reveal the top four cards of your library. Put all Goblin cards revealed this way into your hand and the rest on the bottom of your library in any order";
    }

    public GoblinRingleaderEffect(final GoblinRingleaderEffect effect) {
        super(effect);
    }

    @Override
    public GoblinRingleaderEffect copy() {
        return new GoblinRingleaderEffect(this);
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
