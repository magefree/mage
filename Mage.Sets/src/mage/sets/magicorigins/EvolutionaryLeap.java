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
package mage.sets.magicorigins;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Library;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public class EvolutionaryLeap extends CardImpl {

    public EvolutionaryLeap(UUID ownerId) {
        super(ownerId, 176, "Evolutionary Leap", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");
        this.expansionSetCode = "ORI";

        // {G}, Sacrifice a creature: Reveal cards from the top of your library until you reveal a creature card. Put that card into your hand and the rest on the bottom of your library in a random order.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new EvolutionaryLeapEffect(), new ManaCostsImpl("{G}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(new FilterControlledCreaturePermanent("a creature"))));
        this.addAbility(ability);
    }

    public EvolutionaryLeap(final EvolutionaryLeap card) {
        super(card);
    }

    @Override
    public EvolutionaryLeap copy() {
        return new EvolutionaryLeap(this);
    }
}

class EvolutionaryLeapEffect extends OneShotEffect {

    private static final FilterCreatureCard filter = new FilterCreatureCard();

    public EvolutionaryLeapEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "reveal cards from the top of your library until you reveal a creature card. Put that card into your hand and the rest on the bottom of your library in a random order";
    }

    public EvolutionaryLeapEffect(final EvolutionaryLeapEffect effect) {
        super(effect);
    }

    @Override
    public EvolutionaryLeapEffect copy() {
        return new EvolutionaryLeapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && controller.getLibrary().size() > 0) {
            Cards cards = new CardsImpl();
            Library library = controller.getLibrary();
            Card card = null;
            do {
                card = library.removeFromTop(game);
                if (card != null) {
                    cards.add(card);
                }
            } while (library.size() > 0 && card != null && !filter.match(card, game));
            // reveal cards
            if (!cards.isEmpty()) {
                controller.revealCards(sourceObject.getIdName(), cards, game);
                if (filter.match(card, game)) {
                    // put creature card in hand
                    controller.moveCards(card, Zone.LIBRARY, Zone.HAND, source, game);
                    // remove it from revealed card list
                    cards.remove(card);
                }
                // Put the rest on the bottom of your library in a random order
                Cards randomOrder = new CardsImpl();
                while (cards.size() > 0) {
                    card = cards.getRandom(game);
                    if (card != null) {
                        cards.remove(card);
                        randomOrder.add(card);
                        controller.moveCardToLibraryWithInfo(card, source.getSourceId(), game, Zone.HAND, false, false);
                    }
                }
                controller.putCardsOnBottomOfLibrary(randomOrder, game, source, false);
            }
            return true;
        }
        return false;
    }
}
