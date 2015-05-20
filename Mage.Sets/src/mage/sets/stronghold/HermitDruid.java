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
package mage.sets.stronghold;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.common.FilterBasicLandCard;
import mage.game.Game;
import mage.players.Library;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class HermitDruid extends CardImpl {

    public HermitDruid(UUID ownerId) {
        super(ownerId, 58, "Hermit Druid", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.expansionSetCode = "STH";
        this.subtype.add("Human");
        this.subtype.add("Druid");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {G}, {tap}: Reveal cards from the top of your library until you reveal a basic land card. Put that card into your hand and all other cards revealed this way into your graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new HermitDruidEffect(), new ManaCostsImpl("{G}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);


    }

    public HermitDruid(final HermitDruid card) {
        super(card);
    }

    @Override
    public HermitDruid copy() {
        return new HermitDruid(this);
    }
}

class HermitDruidEffect extends OneShotEffect {

    public HermitDruidEffect() {
        super(Outcome.Benefit);
        this.staticText = "Reveal cards from the top of your library until you reveal a basic land card. Put that card into your hand and all other cards revealed this way into your graveyard";
    }

    public HermitDruidEffect(final HermitDruidEffect effect) {
        super(effect);
    }

    @Override
    public HermitDruidEffect copy() {
        return new HermitDruidEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject= game.getObject(source.getSourceId());
        if (player != null) {

            Library library = player.getLibrary();
            if (library.size() < 1) {
                return true;
            }
            CardsImpl cards = new CardsImpl();
            Card card;
            Filter filter = new FilterBasicLandCard();
            do {
                card = library.removeFromTop(game);
                if (card != null) {
                    
                    if (filter.match(card, game)) {
                        player.moveCardToHandWithInfo(card, source.getSourceId(), game, Zone.LIBRARY);
                    } else {
                        cards.add(card);
                    }
                }
            } while (library.size() > 0 && card != null && !filter.match(card, game));

            if (!cards.isEmpty()) {
                for (Card cardToGrave: cards.getCards(game)) {
                    player.moveCardToGraveyardWithInfo(cardToGrave, source.getSourceId(), game, Zone.LIBRARY);
                }
                if (card != null) {
                    cards.add(card);
                }
                player.revealCards(sourceObject.getName(), cards, game);
            }
            return true;
        }
        return false;
    }
}
