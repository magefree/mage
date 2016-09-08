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
package mage.sets.kaladesh;

import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author emerald000
 */
public class AetherworksMarvel extends CardImpl {

    public AetherworksMarvel(UUID ownerId) {
        super(ownerId, 193, "Aetherworks Marvel", Rarity.MYTHIC, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "KLD";
        this.supertype.add("Legendary");

        // Whenever a permanent you control is put into a graveyard, you get {E}.
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(new GetEnergyCountersControllerEffect(1), false, new FilterPermanent("a permanent"), false));

        // {T}, Pay {E}{E}{E}{E}{E}{E}: Look at the top six cards of your library. You may cast a card from among them without paying its mana cost. Put the rest on the bottom of your library in a random order.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AetherworksMarvelEffect(), new TapSourceCost());
        ability.addCost(new PayEnergyCost(6));
        this.addAbility(ability);
    }

    public AetherworksMarvel(final AetherworksMarvel card) {
        super(card);
    }

    @Override
    public AetherworksMarvel copy() {
        return new AetherworksMarvel(this);
    }
}

class AetherworksMarvelEffect extends OneShotEffect {

    AetherworksMarvelEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Look at the top six cards of your library. You may cast a card from among them without paying its mana cost. Put the rest on the bottom of your library in a random order";
    }

    AetherworksMarvelEffect(final AetherworksMarvelEffect effect) {
        super(effect);
    }

    @Override
    public AetherworksMarvelEffect copy() {
        return new AetherworksMarvelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<Card> cardsSet = controller.getLibrary().getTopCards(game, 6);
            Cards cards = new CardsImpl();
            for (Card card : cardsSet) {
                cards.add(card);
            }
            TargetCard target = new TargetCardInLibrary(0, 1, new FilterNonlandCard("card to cast without paying its mana cost"));
            if (controller.choose(Outcome.PlayForFree, cards, target, game)) {
                Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
                if (card != null && controller.cast(card.getSpellAbility(), game, true)) {
                    cards.remove(card);
                }
            }
            controller.putCardsOnBottomOfLibrary(cards, game, source, false);
            return true;
        }
        return false;
    }
}
