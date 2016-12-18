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
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
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
 * @author ESOF_1617_T5_G3
 */
public class DarkSuspicions extends CardImpl {

    public DarkSuspicions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");

        // At the beginning of each opponent's upkeep, that player loses X life, where X is the number of cards in that player's hand minus the number of cards in your hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DarkSuspicionsEffect(), TargetController.OPPONENT, false));
    }

    public DarkSuspicions(final DarkSuspicions card) {
        super(card);
    }

    @Override
    public DarkSuspicions copy() {
        return new DarkSuspicions(this);
    }
}

class DarkSuspicionsEffect extends OneShotEffect {

    public DarkSuspicionsEffect() {
        super(Outcome.LoseLife);
        staticText = "that player loses X life, where X is the number of cards in that player's hand minus the number of cards in your hand.";
    }

    public DarkSuspicionsEffect(final mage.cards.d.DarkSuspicionsEffect effect) {
        super(effect);
    }

    @Override
    public mage.cards.d.DarkSuspicionsEffect copy() {
        return new mage.cards.d.DarkSuspicionsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (opponent != null) {
            int xValue = opponent.getHand().size() - controller.getHand().size();
            if (xValue > 0) {
                opponent.loseLife(xValue, game, false);
            }
            return true;
        }
        return false;
    }
}