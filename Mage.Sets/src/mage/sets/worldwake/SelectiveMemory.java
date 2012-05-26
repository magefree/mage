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
package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author North
 */
public class SelectiveMemory extends CardImpl<SelectiveMemory> {

    public SelectiveMemory(UUID ownerId) {
        super(ownerId, 37, "Selective Memory", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{3}{U}");
        this.expansionSetCode = "WWK";

        this.color.setBlue(true);

        // Search your library for any number of nonland cards and exile them. Then shuffle your library.
        this.getSpellAbility().addEffect(new SelectiveMemoryEffect());
    }

    public SelectiveMemory(final SelectiveMemory card) {
        super(card);
    }

    @Override
    public SelectiveMemory copy() {
        return new SelectiveMemory(this);
    }
}

class SelectiveMemoryEffect extends OneShotEffect<SelectiveMemoryEffect> {

    public SelectiveMemoryEffect() {
        super(Outcome.Exile);
        this.staticText = "Search your library for any number of nonland cards and exile them. Then shuffle your library";
    }

    public SelectiveMemoryEffect(final SelectiveMemoryEffect effect) {
        super(effect);
    }

    @Override
    public SelectiveMemoryEffect copy() {
        return new SelectiveMemoryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(0, Integer.MAX_VALUE, new FilterNonlandCard());
            if (player.searchLibrary(target, game)) {
                for (UUID targetId : target.getTargets()) {
                    Card card = player.getLibrary().remove(targetId, game);
                    if (card != null) {
                        card.moveToExile(null, "", source.getSourceId(), game);
                    }
                }
            }

            player.getLibrary().shuffle();
            return true;
        }
        return false;
    }
}
