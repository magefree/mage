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
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfDrawTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author noxx
 *
 */
public class TeferisPuzzleBox extends CardImpl {

    public TeferisPuzzleBox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // At the beginning of each player's draw step, that player puts the cards in his or her hand on the bottom of his or her library in any order, then draws that many cards.
        Ability ability = new BeginningOfDrawTriggeredAbility(new TeferisPuzzleBoxEffect(), TargetController.ANY, false);
        this.addAbility(ability);
    }

    public TeferisPuzzleBox(final TeferisPuzzleBox card) {
        super(card);
    }

    @Override
    public TeferisPuzzleBox copy() {
        return new TeferisPuzzleBox(this);
    }
}

class TeferisPuzzleBoxEffect extends OneShotEffect {

    public TeferisPuzzleBoxEffect() {
        super(Outcome.Neutral);
        staticText = "At the beginning of each player's draw step, that player puts the cards in his or her hand on the bottom of his or her library in any order, then draws that many cards";
    }

    public TeferisPuzzleBoxEffect(final TeferisPuzzleBoxEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            int count = player.getHand().size();
            player.putCardsOnBottomOfLibrary(player.getHand(), game, source, true);
            player.drawCards(count, game);
        }
        return true;
    }

    @Override
    public TeferisPuzzleBoxEffect copy() {
        return new TeferisPuzzleBoxEffect(this);
    }

}
