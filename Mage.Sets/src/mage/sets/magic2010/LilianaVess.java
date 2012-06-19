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

package mage.sets.magic2010;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DiscardTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutOnLibraryEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class LilianaVess extends CardImpl<LilianaVess> {

    public LilianaVess(UUID ownerId) {
        super(ownerId, 102, "Liliana Vess", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{3}{B}{B}");
        this.expansionSetCode = "M10";
        this.subtype.add("Liliana");
        this.color.setBlack(true);
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(5)), ""));

        LoyaltyAbility ability1 = new LoyaltyAbility(new DiscardTargetEffect(1), 1);
        ability1.addTarget(new TargetPlayer());
        this.addAbility(ability1);

        this.addAbility(new LoyaltyAbility(new SearchLibraryPutOnLibraryEffect(new TargetCardInLibrary()), -2));

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

class LilianaVessEffect extends OneShotEffect<LilianaVessEffect> {

    public LilianaVessEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Put all creature cards in all graveyards onto the battlefield under your control";
    }

    public LilianaVessEffect(final LilianaVessEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Player player: game.getPlayers().values()) {
            for (Card card: player.getGraveyard().getCards(game)) {
                if (card.getCardType().contains(CardType.CREATURE)) {
                    card.putOntoBattlefield(game, Zone.GRAVEYARD, source.getId(), source.getControllerId());
                }
            }
        }
        return true;
    }

    @Override
    public LilianaVessEffect copy() {
        return new LilianaVessEffect(this);
    }

}
