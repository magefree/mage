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
package mage.sets.ravnica;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TransmuteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
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
public class DimirMachinations extends CardImpl {

    public DimirMachinations(UUID ownerId) {
        super(ownerId, 84, "Dimir Machinations", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{2}{B}");
        this.expansionSetCode = "RAV";

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
            int numCardsToLookAt = Math.min(3, targetPlayer.getLibrary().size());
            if (numCardsToLookAt > 0) {
                CardsImpl cards = new CardsImpl();
                for (int i = 0; i < numCardsToLookAt; i++) {
                    cards.add(targetPlayer.getLibrary().removeFromTop(game));
                }
                TargetCard targetExile = new TargetCard(0, numCardsToLookAt, Zone.LIBRARY, new FilterCard("card to exile"));
                if (controller.choose(Outcome.Exile, cards, targetExile, game)) {
                    for (UUID cardId : targetExile.getTargets()) {
                        Card card = cards.get(cardId, game);
                        if (card != null) {
                            controller.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, Zone.LIBRARY, true);
                            cards.remove(card);
                        }
                    }
                    while (cards.size() > 0) {
                        if (cards.size() == 1) {
                            Card card = cards.get(cards.iterator().next(), game);
                            controller.moveCardToLibraryWithInfo(card, source.getSourceId(), game, Zone.LIBRARY, true, false);
                            cards.remove(card);
                        }
                        else {
                            TargetCard targetTop = new TargetCard(Zone.LIBRARY, new FilterCard("card to put on top of library"));
                            if (controller.choose(Outcome.Neutral, cards, targetTop, game)) {
                                Card card = cards.get(targetTop.getFirstTarget(), game);
                                controller.moveCardToLibraryWithInfo(card, source.getSourceId(), game, Zone.LIBRARY, true, false);
                                cards.remove(card);
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
