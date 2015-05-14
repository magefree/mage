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
package mage.sets.futuresight;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.GravestormAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;
import mage.watchers.common.GravestormWatcher;

/**
 *
 * @author emerald000
 */
public class BitterOrdeal extends CardImpl {

    public BitterOrdeal(UUID ownerId) {
        super(ownerId, 80, "Bitter Ordeal", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{2}{B}");
        this.expansionSetCode = "FUT";

        // Search target player's library for a card and exile it. Then that player shuffles his or her library.
        this.getSpellAbility().addEffect(new BitterOrdealEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        
        // Gravestorm
        this.addAbility(new GravestormAbility(), new GravestormWatcher());
    }

    public BitterOrdeal(final BitterOrdeal card) {
        super(card);
    }

    @Override
    public BitterOrdeal copy() {
        return new BitterOrdeal(this);
    }
}

class BitterOrdealEffect extends OneShotEffect {

    BitterOrdealEffect() {
        super(Outcome.Exile);
        staticText = "Search target player's library for a card and exile it. Then that player shuffles his or her library.";
    }

    BitterOrdealEffect(final BitterOrdealEffect effect) {
        super(effect);
    }

    @Override
    public BitterOrdealEffect copy() {
        return new BitterOrdealEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && targetPlayer != null) {
            TargetCardInLibrary target = new TargetCardInLibrary();
            if (controller.searchLibrary(target, game, targetPlayer.getId())) {
                Card card = targetPlayer.getLibrary().getCard(target.getFirstTarget(), game);
                if (card != null) {
                    controller.moveCardToExileWithInfo(card, null, null, source.getSourceId(), game, Zone.LIBRARY, true);
                }
            }
            targetPlayer.shuffleLibrary(game);
            return true;
        }
        return false;
    }
}
