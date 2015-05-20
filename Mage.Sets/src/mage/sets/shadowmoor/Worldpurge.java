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
package mage.sets.shadowmoor;

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
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.other.OwnerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author jeffwadsworth
 */
public class Worldpurge extends CardImpl {

    public Worldpurge(UUID ownerId) {
        super(ownerId, 156, "Worldpurge", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{4}{W/U}{W/U}{W/U}{W/U}");
        this.expansionSetCode = "SHM";


        // Return all permanents to their owners' hands. Each player chooses up to seven cards in his or her hand, then shuffles the rest into his or her library. Empty all mana pools.
        this.getSpellAbility().addEffect(new WorldpurgeEffect());

    }

    public Worldpurge(final Worldpurge card) {
        super(card);
    }

    @Override
    public Worldpurge copy() {
        return new Worldpurge(this);
    }
}

class WorldpurgeEffect extends OneShotEffect {

    public WorldpurgeEffect() {
        super(Outcome.Discard);
        this.staticText = "Return all permanents to their owners' hands. Each player chooses up to seven cards in his or her hand, then shuffles the rest into his or her library. Empty all mana pools.";
    }

    public WorldpurgeEffect(final WorldpurgeEffect effect) {
        super(effect);
    }

    @Override
    public WorldpurgeEffect copy() {
        return new WorldpurgeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterPermanent(), source.getControllerId(), source.getSourceId(), game)) {
            permanent.moveToZone(Zone.HAND, source.getSourceId(), game, false);
        }
        game.informPlayers("Worldpurge: All permanents returned to owners' hands");
        for (Player player : game.getPlayers().values()) {
            if (player != null) {
                Cards hand = player.getHand();
                FilterCard filter = new FilterCard("card to keep in hand");
                filter.add(new OwnerIdPredicate(player.getId()));
                int numberInHand = Math.min(7, hand.size());
                TargetCardInHand target = new TargetCardInHand(0, numberInHand, filter);
                if (player.choose(Outcome.Benefit, target, source.getSourceId(), game)) {
                    for (Card card : hand.getCards(game)) {
                        if (!target.getTargets().contains(card.getId())) {
                            card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, false);
                        }
                    }
                }
                player.shuffleLibrary(game);
            }
        }
        game.emptyManaPools();
        game.informPlayers("Worldpurge: All mana pools have been emptied");
        return true;
    }
}
