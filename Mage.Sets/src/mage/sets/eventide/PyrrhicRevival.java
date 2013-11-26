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
package mage.sets.eventide;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 *
 */
public class PyrrhicRevival extends CardImpl<PyrrhicRevival> {

    public PyrrhicRevival(UUID ownerId) {
        super(ownerId, 93, "Pyrrhic Revival", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{3}{W/B}{W/B}{W/B}");
        this.expansionSetCode = "EVE";

        this.color.setBlack(true);
        this.color.setWhite(true);

        // Each player returns each creature card from his or her graveyard to the battlefield with an additional -1/-1 counter on it.
        this.getSpellAbility().addEffect(new PyrrhicRevivalEffect());

    }

    public PyrrhicRevival(final PyrrhicRevival card) {
        super(card);
    }

    @Override
    public PyrrhicRevival copy() {
        return new PyrrhicRevival(this);
    }
}

class PyrrhicRevivalEffect extends OneShotEffect<PyrrhicRevivalEffect> {

    public PyrrhicRevivalEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Each player returns each creature card from his or her graveyard to the battlefield with an additional -1/-1 counter on it";
    }

    public PyrrhicRevivalEffect(final PyrrhicRevivalEffect effect) {
        super(effect);
    }

    @Override
    public PyrrhicRevivalEffect copy() {
        return new PyrrhicRevivalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;
        for (Player player : game.getPlayers().values()) {
            if (player != null) {
                Cards cards = player.getGraveyard();
                for (Card card : cards.getCards(game)) {
                    if (card != null && card.getCardType().contains(CardType.CREATURE)) {
                        if (card.putOntoBattlefield(game, Zone.GRAVEYARD, source.getSourceId(), card.getOwnerId(), false)) {
                            Permanent permanent = game.getPermanent(card.getId());
                            if (permanent != null) {
                                permanent.addCounters(CounterType.M1M1.createInstance(), game);
                            }
                            result = true;
                        }
                    }
                }
            }
        }
        return result;
    }
}