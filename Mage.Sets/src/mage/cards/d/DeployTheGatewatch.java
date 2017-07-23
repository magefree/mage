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
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author fireshoes
 */
public class DeployTheGatewatch extends CardImpl {

    public DeployTheGatewatch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{W}{W}");

        // Look at the top seven cards of your library. Put up to two planeswalker cards from among them onto the battlefield.
        // Put the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new DeployTheGatewatchEffect());
    }

    public DeployTheGatewatch(final DeployTheGatewatch card) {
        super(card);
    }

    @Override
    public DeployTheGatewatch copy() {
        return new DeployTheGatewatch(this);
    }
}

class DeployTheGatewatchEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("up to two planeswalker cards");

    static {
        filter.add(new CardTypePredicate(CardType.PLANESWALKER));
    }

    public DeployTheGatewatchEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Look at the top seven cards of your library. Put up to two planeswalker cards from among them onto the battlefield. "
                + "Put the rest on the bottom of your library in a random order";
    }

    public DeployTheGatewatchEffect(final DeployTheGatewatchEffect effect) {
        super(effect);
    }

    @Override
    public DeployTheGatewatchEffect copy() {
        return new DeployTheGatewatchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        // Look at the top seven cards of your library.
        Cards cards = new CardsImpl();
        boolean planeswalkerIncluded = false;
        for (int i = 0; i < 7; i++) {
            Card card = player.getLibrary().removeFromTop(game);
            if (card != null) {
                cards.add(card);
                if (filter.match(card, game)) {
                    planeswalkerIncluded = true;
                }
            }
        }
        player.lookAtCards("Deploy the Gatewatch", cards, game);

        // Put up to two planeswalker cards from among them onto the battlefield.
        if (planeswalkerIncluded) {
            TargetCard target = new TargetCard(0, 2, Zone.LIBRARY, filter);
            if (player.choose(Outcome.DrawCard, cards, target, game)) {
                Cards pickedCards = new CardsImpl(target.getTargets());
                cards.removeAll(pickedCards);
                player.moveCards(pickedCards.getCards(game), Zone.BATTLEFIELD, source, game);
            }
        }

        // Put the rest on the bottom of your library in a random order
        while (!cards.isEmpty()) {
            Card card = cards.getRandom(game);
            if (card != null) {
                cards.remove(card);
                card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, false);
            }
        }
        return true;
    }
}
