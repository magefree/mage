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
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PreventDamageByTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterBlockingCreature;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public class FightingChance extends CardImpl {

    private static final FilterBlockingCreature filter = new FilterBlockingCreature("Blocking creatures");

    public FightingChance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // For each blocking creature, flip a coin. If you win the flip, prevent all combat damage that would be dealt by that creature this turn.
        this.getSpellAbility().addEffect(new FightingChanceEffect());
    }

    public FightingChance(final FightingChance card) {
        super(card);
    }

    @Override
    public FightingChance copy() {
        return new FightingChance(this);
    }
}

class FightingChanceEffect extends OneShotEffect {

    FightingChanceEffect() {
        super(Outcome.Detriment);
        staticText = "For each blocking creature, flip a coin. If you win the flip, prevent all combat damage that would be dealt by that creature this turn.";
    }

    FightingChanceEffect(final FightingChanceEffect effect) {
        super(effect);
    }

    @Override
    public FightingChanceEffect copy() {
        return new FightingChanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            for (UUID blocker : game.getCombat().getBlockers()) {
                if (player.flipCoin(game)) {
                    PreventDamageByTargetEffect effect = new PreventDamageByTargetEffect(Duration.EndOfTurn, true);
                    effect.setTargetPointer(new FixedTarget(blocker));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}
