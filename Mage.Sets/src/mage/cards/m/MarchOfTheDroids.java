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
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.token.DroidToken;
import mage.players.Player;

/**
 *
 * @author Styxo
 */
public class MarchOfTheDroids extends CardImpl {

    public MarchOfTheDroids(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}{U}{B}");

        // Remove all repair counters from all cards in your graveyard. Return each card with a repair counter removed this way from graveyard to the battlefield.
        this.getSpellAbility().addEffect(new MarchOfTheDroidsEffect());

        // Create 1/1 colorles Droid artifact creature token for each Droid you control.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new DroidToken(), new PermanentsOnBattlefieldCount(new FilterPermanent(SubType.DROID, "Droid you control"))));
    }

    public MarchOfTheDroids(final MarchOfTheDroids card) {
        super(card);
    }

    @Override
    public MarchOfTheDroids copy() {
        return new MarchOfTheDroids(this);
    }
}

class MarchOfTheDroidsEffect extends OneShotEffect {

    public MarchOfTheDroidsEffect() {
        super(Outcome.Benefit);
        this.staticText = "Remove all repair counters from all cards in your graveyard. Return each card with a repair counter removed this way from graveyard to the battlefield";
    }

    public MarchOfTheDroidsEffect(final MarchOfTheDroidsEffect effect) {
        super(effect);
    }

    @Override
    public MarchOfTheDroidsEffect copy() {
        return new MarchOfTheDroidsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cardsToReturn = new CardsImpl();
            for (Card card : controller.getGraveyard().getCards(game)) {
                if (card.getOwnerId().equals(controller.getId()) && card.getCounters(game).getCount(CounterType.REPAIR) > 0) {
                    int number = card.getCounters(game).getCount(CounterType.REPAIR);
                    if (number > 0) {
                        cardsToReturn.add(card);
                        card.removeCounters(CounterType.REPAIR.createInstance(number), game);
                    }
                }
            }
            if (!cardsToReturn.isEmpty()) {
                controller.moveCards(cardsToReturn, Zone.BATTLEFIELD, source, game);
            }
            return true;
        }
        return false;
    }
}
