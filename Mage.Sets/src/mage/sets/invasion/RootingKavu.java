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
package mage.sets.invasion;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Derpthemeus
 */
public class RootingKavu extends CardImpl {

    public RootingKavu(UUID ownerId) {
        super(ownerId, 207, "Rooting Kavu", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.expansionSetCode = "INV";
        this.subtype.add("Kavu");
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Rooting Kavu dies, you may exile it. If you do, shuffle all creature cards from your graveyard into your library.
        this.addAbility(new DiesTriggeredAbility(new DoIfCostPaid(new RootingKavuEffect(), new ExileSourceFromGraveCost())));
    }

    public RootingKavu(final RootingKavu card) {
        super(card);
    }

    @Override
    public RootingKavu copy() {
        return new RootingKavu(this);
    }

    class RootingKavuEffect extends OneShotEffect {

        public RootingKavuEffect() {
            super(Outcome.Benefit);
            this.staticText = "shuffle all creature cards from your graveyard into your library.";
        }

        public RootingKavuEffect(final RootingKavuEffect effect) {
            super(effect);
        }

        @Override
        public RootingKavuEffect copy() {
            return new RootingKavuEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                Cards cards = new CardsImpl();
                cards.addAll(controller.getGraveyard().getCards(new FilterCreatureCard(), game));
                controller.putCardsOnTopOfLibrary(cards, game, source, false);
                controller.shuffleLibrary(source, game);
                return true;
            }
            return false;
        }
    }
}
