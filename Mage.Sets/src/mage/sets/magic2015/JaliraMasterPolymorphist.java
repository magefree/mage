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
package mage.sets.magic2015;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
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
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.players.Library;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class JaliraMasterPolymorphist extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another creature");

    static {
        filter.add(new AnotherPredicate());
    }

    public JaliraMasterPolymorphist(UUID ownerId) {
        super(ownerId, 64, "Jalira, Master Polymorphist", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.expansionSetCode = "M15";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {2}{U}, {T}, Sacrifice another creature: Reveal cards from the top of your library until you reveal a nonlegendary creature card.
        // Put that card onto the battlefield and the rest on the bottom of your library in a random order.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new JaliraMasterPolymorphistEffect(), new ManaCostsImpl("{2}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filter, true)));
        this.addAbility(ability);

    }

    public JaliraMasterPolymorphist(final JaliraMasterPolymorphist card) {
        super(card);
    }

    @Override
    public JaliraMasterPolymorphist copy() {
        return new JaliraMasterPolymorphist(this);
    }
}

class JaliraMasterPolymorphistEffect extends OneShotEffect {

    private static final FilterCreatureCard filter = new FilterCreatureCard("nonlegendary creature card");

    static {
        filter.add(Predicates.not(new SupertypePredicate("Legendary")));
    }

    public JaliraMasterPolymorphistEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Reveal cards from the top of your library until you reveal a nonlegendary creature card. Put that card onto the battlefield and the rest on the bottom of your library in a random order";
    }

    public JaliraMasterPolymorphistEffect(final JaliraMasterPolymorphistEffect effect) {
        super(effect);
    }

    @Override
    public JaliraMasterPolymorphistEffect copy() {
        return new JaliraMasterPolymorphistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && controller.getLibrary().size() > 0) {
            CardsImpl cards = new CardsImpl();
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
                controller.revealCards(sourceObject.getName(), cards, game);
            }
            // put nonlegendary creature card to battlefield
            controller.putOntoBattlefieldWithInfo(card, game, Zone.LIBRARY, source.getSourceId());
            // remove it from revealed card list
            cards.remove(card);
            // Put the rest on the bottom of your library in a random order
            while (cards.size() > 0) {
                card = cards.getRandom(game);
                if (card != null) {
                    cards.remove(card);
                    controller.moveCardToLibraryWithInfo(card, source.getSourceId(), game, Zone.HAND, false, false);
                }
            }
            return true;
        }
        return false;
    }
}
