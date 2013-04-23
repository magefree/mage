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
package mage.sets.shardsofalara;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.Filter.ComparisonType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author North
 */
public class MayaelTheAnima extends CardImpl<MayaelTheAnima> {

    public MayaelTheAnima(UUID ownerId) {
        super(ownerId, 179, "Mayael the Anima", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{R}{G}{W}");
        this.expansionSetCode = "ALA";
        this.supertype.add("Legendary");
        this.subtype.add("Elf");
        this.subtype.add("Shaman");

        this.color.setRed(true);
        this.color.setGreen(true);
        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {3}{R}{G}{W}, {tap}: Look at the top five cards of your library.
        // You may put a creature card with power 5 or greater from among them onto the battlefield.
        // Put the rest on the bottom of your library in any order.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new MayaelTheAnimaEffect(),
                new ManaCostsImpl("{3}{R}{G}{W}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public MayaelTheAnima(final MayaelTheAnima card) {
        super(card);
    }

    @Override
    public MayaelTheAnima copy() {
        return new MayaelTheAnima(this);
    }
}

class MayaelTheAnimaEffect extends OneShotEffect<MayaelTheAnimaEffect> {

    public MayaelTheAnimaEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Look at the top five cards of your library. You may put a creature card with power 5 or greater from among them onto the battlefield. Put the rest on the bottom of your library in any order";
    }

    public MayaelTheAnimaEffect(final MayaelTheAnimaEffect effect) {
        super(effect);
    }

    @Override
    public MayaelTheAnimaEffect copy() {
        return new MayaelTheAnimaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        FilterCreatureCard filterCreatureCard = new FilterCreatureCard("creature card with power 5 or greater to put onto the battlefield");
        filterCreatureCard.add(new PowerPredicate(ComparisonType.GreaterThan, 4));

        Cards cards = new CardsImpl(Zone.PICK);
        boolean creatureCardFound = false;
        int count = Math.min(player.getLibrary().size(), 5);
        for (int i = 0; i < count; i++) {
            Card card = player.getLibrary().removeFromTop(game);
            if (card != null) {
                cards.add(card);
                game.setZone(card.getId(), Zone.PICK);
                if (filterCreatureCard.match(card, game)) {
                    creatureCardFound = true;
                }
            }
        }
        player.lookAtCards("Mayael the Anima", cards, game);

        if (creatureCardFound && player.chooseUse(Outcome.DrawCard, "Do you wish to put a creature card with power 5 or greater from among them onto the battlefield?", game)) {
            TargetCard target = new TargetCard(Zone.PICK, filterCreatureCard);
            if (player.choose(this.outcome, cards, target, game)) {
                Card card = cards.get(target.getFirstTarget(), game);
                if (card != null) {
                    cards.remove(card);
                    card.putOntoBattlefield(game, Zone.PICK, source.getId(), player.getId());
                }
            }
        }

        TargetCard target = new TargetCard(Zone.PICK, new FilterCard("card to put on the bottom of your library"));
        target.setRequired(true);
        while (cards.size() > 1) {
            player.choose(Outcome.Neutral, cards, target, game);
            Card card = cards.get(target.getFirstTarget(), game);
            if (card != null) {
                cards.remove(card);
                card.moveToZone(Zone.LIBRARY, source.getId(), game, false);
            }
            target.clearChosen();
        }
        if (cards.size() == 1) {
            Card card = cards.get(cards.iterator().next(), game);
            card.moveToZone(Zone.LIBRARY, source.getId(), game, false);
        }

        return true;
    }
}
