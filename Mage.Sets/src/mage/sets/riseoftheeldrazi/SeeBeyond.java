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

package mage.sets.riseoftheeldrazi;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SeeBeyond extends CardImpl<SeeBeyond> {

    public SeeBeyond(UUID ownerId) {
        super(ownerId, 86, "See Beyond", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{1}{U}");
        this.expansionSetCode = "ROE";
        this.color.setBlue(true);
        this.getSpellAbility().addEffect(new SeeBeyondEffect());
    }

    public SeeBeyond(final SeeBeyond card) {
        super(card);
    }

    @Override
    public SeeBeyond copy() {
        return new SeeBeyond(this);
    }

}

class SeeBeyondEffect extends OneShotEffect<SeeBeyondEffect> {

    private static FilterCard filter = new FilterCard("card to shuffle into your library");

    public SeeBeyondEffect() {
        super(Outcome.DrawCard);
        staticText = "Draw two cards, then shuffle a card from your hand into your library";
    }

    public SeeBeyondEffect(SeeBeyondEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        player.drawCards(2, game);
        if (player.getHand().size() > 0) {
            TargetCard target = new TargetCard(Zone.HAND, filter);
            target.setRequired(true);
            player.choose(Outcome.Detriment, player.getHand(), target, game);
            Card card = player.getHand().get(target.getFirstTarget(), game);
            if (card != null) {
                player.removeFromHand(card, game);
                card.moveToZone(Zone.LIBRARY, source.getId(), game, true);
                player.shuffleLibrary(game);
                return true;
            }
        }
        return true;
    }

    @Override
    public SeeBeyondEffect copy() {
        return new SeeBeyondEffect(this);
    }

}