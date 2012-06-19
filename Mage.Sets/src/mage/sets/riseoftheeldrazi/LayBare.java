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
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.cards.Card;

/**
 *
 * @author jeffwadsworth
 */
public class LayBare extends CardImpl<LayBare> {

    public LayBare(UUID ownerId) {
        super(ownerId, 74, "Lay Bare", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");
        this.expansionSetCode = "ROE";

        this.color.setBlue(true);

        // Counter target spell. Look at its controller's hand.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new LayBareEffect());
    }

    public LayBare(final LayBare card) {
        super(card);
    }

    @Override
    public LayBare copy() {
        return new LayBare(this);
    }
}

class LayBareEffect extends OneShotEffect<LayBareEffect> {

    public LayBareEffect() {
        super(Outcome.Benefit);
        staticText = "Look at its controller's hand";
    }

    public LayBareEffect(final LayBareEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card target = (Card) game.getLastKnownInformation(targetPointer.getFirst(game, source), Constants.Zone.STACK);
        if (target != null) {
            Player controller = game.getPlayer(target.getOwnerId());
            if (controller != null) {
                player.lookAtCards("Lay Bare", controller.getHand(), game);
                return true;
            }
        }
        return false;
    }

    @Override
    public LayBareEffect copy() {
        return new LayBareEffect(this);
    }
}