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
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public class RowdyCrew extends CardImpl {

    public RowdyCrew(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add("Human");
        this.subtype.add("Pirate");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Rowdy Crew enters the battlefield, draw three cards, then discard two cards at random. If two cards that share a card type are discarded this way, put two +1/+1 counters on Rowdy Crew.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new RowdyCrewEffect()));
    }

    public RowdyCrew(final RowdyCrew card) {
        super(card);
    }

    @Override
    public RowdyCrew copy() {
        return new RowdyCrew(this);
    }
}

class RowdyCrewEffect extends OneShotEffect {

    RowdyCrewEffect() {
        super(Outcome.Benefit);
        this.staticText = "draw three cards, then discard two cards at random. If two cards that share a card type are discarded this way, put two +1/+1 counters on {this}";
    }

    RowdyCrewEffect(final RowdyCrewEffect effect) {
        super(effect);
    }

    @Override
    public RowdyCrewEffect copy() {
        return new RowdyCrewEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getSourceId());
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.drawCards(3, game);
            Cards cards = new CardsImpl();
            int cardsInHand = player.getHand().size();
            switch (cardsInHand) {
                case 0:
                    break;
                case 1:
                    player.discard(1, true, source, game);
                    break;
                default:
                    cards = player.discard(2, true, source, game);
            }
            if (creature != null && cardsInHand > 1) {
                for (CardType type : CardType.values()) {
                    int count = 0;
                    for (UUID cardId : cards) {
                        if (game.getCard(cardId).getCardType().contains(type)) {
                            count++;
                            if (count > 1) {
                                creature.addCounters(CounterType.P1P1.createInstance(2), source, game);
                                return true;
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
