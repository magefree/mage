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
package mage.sets.dragonsoftarkir;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.AttackingCreatureCount;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class FoulTongueShriek extends CardImpl {

    public FoulTongueShriek(UUID ownerId) {
        super(ownerId, 103, "Foul-Tongue Shriek", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{B}");
        this.expansionSetCode = "DTK";

        // Target opponent loses 1 life for each attacking creature you control. You gain that much life.
        this.getSpellAbility().addEffect(new FoulTongueShriekEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

    }

    public FoulTongueShriek(final FoulTongueShriek card) {
        super(card);
    }

    @Override
    public FoulTongueShriek copy() {
        return new FoulTongueShriek(this);
    }
}

class FoulTongueShriekEffect extends OneShotEffect {

    public FoulTongueShriekEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target opponent loses 1 life for each attacking creature you control. You gain that much life";
    }

    public FoulTongueShriekEffect(final FoulTongueShriekEffect effect) {
        super(effect);
    }

    @Override
    public FoulTongueShriekEffect copy() {
        return new FoulTongueShriekEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetOpponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller != null && targetOpponent != null) {
            int amount = new AttackingCreatureCount().calculate(game, source, this);
            if (amount > 0) {
                targetOpponent.loseLife(amount, game);
                controller.gainLife(amount, game);
            }
            return true;
        }
        return false;
    }
}
