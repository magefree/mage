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
package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutOnLibraryEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class LilianaVess extends CardImpl {

    public LilianaVess(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{B}{B}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Liliana");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(5));
        // +1: Target player discards a card.
        LoyaltyAbility ability1 = new LoyaltyAbility(new DiscardTargetEffect(1), 1);
        ability1.addTarget(new TargetPlayer());
        this.addAbility(ability1);

        // -2: Search your library for a card, then shuffle your library and put that card on top of it.
        this.addAbility(new LoyaltyAbility(new SearchLibraryPutOnLibraryEffect(new TargetCardInLibrary()), -2));

        // -8: Put all creature cards from all graveyards onto the battlefield under your control.
        this.addAbility(new LoyaltyAbility(new LilianaVessEffect(), -8));

    }

    public LilianaVess(final LilianaVess card) {
        super(card);
    }

    @Override
    public LilianaVess copy() {
        return new LilianaVess(this);
    }

}

class LilianaVessEffect extends OneShotEffect {

    public LilianaVessEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Put all creature cards from all graveyards onto the battlefield under your control";
    }

    public LilianaVessEffect(final LilianaVessEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<Card> creatureCards = new LinkedHashSet<>();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    creatureCards.addAll(player.getGraveyard().getCards(new FilterCreatureCard(), game));
                }
            }
            controller.moveCards(creatureCards, Zone.BATTLEFIELD, source, game, false, false, false, null);
            return true;
        }
        return false;
    }

    @Override
    public LilianaVessEffect copy() {
        return new LilianaVessEffect(this);
    }

}
