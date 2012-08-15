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
package mage.sets.fifthedition;

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
import mage.target.common.TargetCardInHand;

/**
 *
 * @author jonubuu
 */
public class Brainstorm extends CardImpl<Brainstorm> {

    public Brainstorm(UUID ownerId) {
        super(ownerId, 76, "Brainstorm", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{U}");
        this.expansionSetCode = "5ED";

        this.color.setBlue(true);

        // Draw three cards, then put two cards from your hand on top of your library in any order.
        this.getSpellAbility().addEffect(new BrainstormEffect());
    }

    public Brainstorm(final Brainstorm card) {
        super(card);
    }

    @Override
    public Brainstorm copy() {
        return new Brainstorm(this);
    }
}

class BrainstormEffect extends OneShotEffect<BrainstormEffect> {

    public BrainstormEffect() {
        super(Outcome.DrawCard);
        staticText = "Draw three cards, then put two cards from your hand on top of your library in any order";
    }

    public BrainstormEffect(final BrainstormEffect effect) {
        super(effect);
    }

    @Override
    public BrainstormEffect copy() {
        return new BrainstormEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.drawCards(3, game);
            putOnLibrary(player, source, game);
            putOnLibrary(player, source, game);
            return true;
        }
        return false;
    }

    private boolean putOnLibrary(Player player, Ability source, Game game) {
        TargetCardInHand target = new TargetCardInHand();
        target.setRequired(true);
        player.chooseTarget(Outcome.ReturnToHand, target, source, game);
        Card card = player.getHand().get(target.getFirstTarget(), game);
        if (card != null) {
            player.getHand().remove(card);
            card.moveToZone(Zone.LIBRARY, source.getId(), game, true);
        }
        return true;
    }
}
