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
import mage.abilities.keyword.TransmuteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

/**
 *
 * @author emerald000
 */
public final class DimirMachinations extends CardImpl {

    public DimirMachinations(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Look at the top three cards of target player's library. Exile any number of those cards, then put the rest back in any order.
        this.getSpellAbility().addEffect(new DimirMachinationsEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Transmute {1}{B}{B}
        this.addAbility(new TransmuteAbility("{1}{B}{B}"));

    }

    public DimirMachinations(final DimirMachinations card) {
        super(card);
    }

    @Override
    public DimirMachinations copy() {
        return new DimirMachinations(this);
    }
}

class DimirMachinationsEffect extends OneShotEffect {

    DimirMachinationsEffect() {
        super(Outcome.Neutral);
        this.staticText = "Look at the top three cards of target player's library. Exile any number of those cards, then put the rest back in any order";
    }

    DimirMachinationsEffect(final DimirMachinationsEffect effect) {
        super(effect);
    }

    @Override
    public DimirMachinationsEffect copy() {
        return new DimirMachinationsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (controller != null && targetPlayer != null) {
            CardsImpl cards = new CardsImpl(targetPlayer.getLibrary().getTopCards(game, 3));
            if (!cards.isEmpty()) {
                controller.lookAtCards(source, null, cards, game);
                TargetCard targetExile = new TargetCard(0, Integer.MAX_VALUE, Zone.LIBRARY, new FilterCard("cards to exile"));
                if (controller.choose(Outcome.Exile, cards, targetExile, game)) {
                    Cards toExile = new CardsImpl(targetExile.getTargets());
                    controller.moveCards(toExile, Zone.EXILED, source, game);
                    cards.removeAll(toExile);
                }
                controller.putCardsOnTopOfLibrary(cards, game, source, true);
            }
            return true;
        }
        return false;
    }
}
