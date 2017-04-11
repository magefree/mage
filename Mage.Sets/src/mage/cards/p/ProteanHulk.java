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
package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author emerald000
 */
public class ProteanHulk extends CardImpl {

    public ProteanHulk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}{G}");
        this.subtype.add("Beast");

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // When Protean Hulk dies, search your library for any number of creature cards with total converted mana cost 6 or less and put them onto the battlefield. Then shuffle your library.
        this.addAbility(new DiesTriggeredAbility(new ProteanHulkEffect()));
    }

    public ProteanHulk(final ProteanHulk card) {
        super(card);
    }

    @Override
    public ProteanHulk copy() {
        return new ProteanHulk(this);
    }
}

class ProteanHulkEffect extends OneShotEffect {

    ProteanHulkEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "search your library for any number of creature cards with total converted mana cost 6 or less and put them onto the battlefield. Then shuffle your library";
    }

    ProteanHulkEffect(final ProteanHulkEffect effect) {
        super(effect);
    }

    @Override
    public ProteanHulkEffect copy() {
        return new ProteanHulkEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cardsPicked = this.ProteanHulkSearch(game, source);
            controller.moveCards(cardsPicked.getCards(game), Zone.BATTLEFIELD, source, game);
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }

    Cards ProteanHulkSearch(Game game, Ability source) {
        Cards cardsPicked = new CardsImpl();
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            GameEvent event = GameEvent.getEvent(GameEvent.EventType.SEARCH_LIBRARY, source.getControllerId(), source.getControllerId(), source.getControllerId(), Integer.MAX_VALUE);
            if (!game.replaceEvent(event)) {
                int manaCostLeftToFetch = 6;
                int librarySearchLimit = event.getAmount();

                FilterCard filter = new FilterCreatureCard("number of creature cards with total converted mana cost 6 or less (6 CMC left)");
                filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, manaCostLeftToFetch + 1));
                TargetCardInLibrary target = new TargetCardInLibrary(0, 1, filter);
                target.setCardLimit(librarySearchLimit);

                while (target.canChoose(source.getSourceId(), source.getControllerId(), game)) {
                    target.choose(Outcome.PutCreatureInPlay, source.getControllerId(), source.getControllerId(), game);
                    Card card = player.getLibrary().remove(target.getFirstTarget(), game);
                    if (card == null) {
                        break;
                    }
                    cardsPicked.add(card);
                    game.getState().getLookedAt(source.getControllerId()).add("Protean Hulk", cardsPicked);

                    librarySearchLimit--;
                    if (librarySearchLimit == 0) {
                        break;
                    }
                    manaCostLeftToFetch -= card.getConvertedManaCost();
                    filter = new FilterCreatureCard("number of creature cards with total converted mana cost 6 or less (" + manaCostLeftToFetch + " CMC left)");
                    filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, manaCostLeftToFetch + 1));
                    target = new TargetCardInLibrary(0, 1, filter);
                    target.setCardLimit(librarySearchLimit);
                }
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.LIBRARY_SEARCHED, source.getControllerId(), source.getControllerId()));
            }
        }
        return cardsPicked;
    }
}
