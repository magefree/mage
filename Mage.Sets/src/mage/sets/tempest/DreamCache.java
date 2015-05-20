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
package mage.sets.tempest;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author Quercitron
 */
public class DreamCache extends CardImpl {

    public DreamCache(UUID ownerId) {
        super(ownerId, 59, "Dream Cache", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{2}{U}");
        this.expansionSetCode = "TMP";


        // Draw three cards, then put two cards from your hand both on top of your library or both on the bottom of your library.
        this.getSpellAbility().addEffect(new DreamCacheEffect());
    }

    public DreamCache(final DreamCache card) {
        super(card);
    }

    @Override
    public DreamCache copy() {
        return new DreamCache(this);
    }
}

class DreamCacheEffect extends OneShotEffect {

    public DreamCacheEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Draw three cards, then put two cards from your hand both on top of your library or both on the bottom of your library.";
    }
    
    public DreamCacheEffect(final DreamCacheEffect effect) {
        super(effect);
    }

    @Override
    public DreamCacheEffect copy() {
        return new DreamCacheEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.drawCards(3, game);
            
            boolean putOnTop = player.chooseUse(Outcome.Neutral, "Put cards on top?", game);
            putInLibrary(player, source, game, putOnTop);
            putInLibrary(player, source, game, putOnTop);
            
            return true;
        }
        return false;
    }
    
    private boolean putInLibrary(Player player, Ability source, Game game, boolean putOnTop) {
        if (player.getHand().size() > 0) {
            TargetCardInHand target = new TargetCardInHand();
            player.chooseTarget(Outcome.Detriment, target, source, game);
            Card card = player.getHand().get(target.getFirstTarget(), game);
            if (card != null) {
                player.getHand().remove(card);
                card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, putOnTop);
                return true;
            }
        }
        return false;
    }
}
