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
package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author emerald000
 */
public class BoldwyrHeavyweights extends CardImpl {

    public BoldwyrHeavyweights(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.GIANT, SubType.WARRIOR);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Boldwyr Heavyweights enters the battlefield, each opponent may search his or her library for a creature card and put it onto the battlefield. Then each player who searched his or her library this way shuffles it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BoldwyrHeavyweightsEffect()));
    }

    public BoldwyrHeavyweights(final BoldwyrHeavyweights card) {
        super(card);
    }

    @Override
    public BoldwyrHeavyweights copy() {
        return new BoldwyrHeavyweights(this);
    }
}

class BoldwyrHeavyweightsEffect extends OneShotEffect {

    BoldwyrHeavyweightsEffect() {
        super(Outcome.Detriment);
        this.staticText = "each opponent may search his or her library for a creature card and put it onto the battlefield. Then each player who searched his or her library this way shuffles it";
    }

    BoldwyrHeavyweightsEffect(final BoldwyrHeavyweightsEffect effect) {
        super(effect);
    }

    @Override
    public BoldwyrHeavyweightsEffect copy() {
        return new BoldwyrHeavyweightsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<Player> playersThatSearched = new HashSet<>(1);
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null && opponent.chooseUse(Outcome.PutCreatureInPlay, "Search your library for a creature card and put it onto the battlefield?", source, game)) {
                TargetCardInLibrary target = new TargetCardInLibrary(new FilterCreatureCard());
                if (opponent.searchLibrary(target, game)) {
                    Card targetCard = opponent.getLibrary().getCard(target.getFirstTarget(), game);
                    if (targetCard != null) {
                        opponent.moveCards(targetCard, Zone.BATTLEFIELD, source, game);
                        playersThatSearched.add(opponent);
                    }
                }
            }
        }
        for (Player opponent : playersThatSearched) {
            opponent.shuffleLibrary(source, game);
        }
        return true;
    }
}
