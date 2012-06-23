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
package mage.sets.mirrodinbesieged;

import java.util.Set;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author North
 */
public class DistantMemories extends CardImpl<DistantMemories> {

    public DistantMemories(UUID ownerId) {
        super(ownerId, 24, "Distant Memories", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{2}{U}{U}");
        this.expansionSetCode = "MBS";

        this.color.setBlue(true);

        this.getSpellAbility().addEffect(new DistantMemoriesEffect());
    }

    public DistantMemories(final DistantMemories card) {
        super(card);
    }

    @Override
    public DistantMemories copy() {
        return new DistantMemories(this);
    }
}

class DistantMemoriesEffect extends OneShotEffect<DistantMemoriesEffect> {

    public DistantMemoriesEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Search your library for a card, exile it, then shuffle your library. Any opponent may have you put that card into your hand. If no player does, you draw three cards";
    }

    public DistantMemoriesEffect(final DistantMemoriesEffect effect) {
        super(effect);
    }

    @Override
    public DistantMemoriesEffect copy() {
        return new DistantMemoriesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        TargetCardInLibrary target = new TargetCardInLibrary();
        target.setRequired(true);
        if (player.searchLibrary(target, game)) {
            Card card = player.getLibrary().remove(target.getFirstTarget(), game);
            if (card != null) {
                card.moveToZone(Zone.EXILED, source.getId(), game, false);
                player.shuffleLibrary(game);

                StringBuilder sb = new StringBuilder();
                sb.append("Have ").append(player.getName()).append(" put ").append(card.getName());
                sb.append(" in his hand? If none of his opponents says yes, he'll draw three cards.");

                boolean putInHand = false;
                Set<UUID> opponents = game.getOpponents(source.getControllerId());
                for (UUID opponentUuid : opponents) {
                    Player opponent = game.getPlayer(opponentUuid);
                    if (opponent != null && !putInHand && opponent.chooseUse(Outcome.Neutral, sb.toString(), game)) {
                        putInHand = true;
                    }
                }

                if (putInHand) {
                    game.getExile().getPermanentExile().remove(card);
                    card.moveToZone(Zone.HAND, source.getId(), game, false);
                } else {
                    player.drawCards(3, game);
                }
                return true;
            }
        }
        player.shuffleLibrary(game);
        return false;
    }
}
