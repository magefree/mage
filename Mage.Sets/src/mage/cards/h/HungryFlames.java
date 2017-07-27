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

package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author JRHerlehy
 */
public class HungryFlames extends CardImpl {

    public HungryFlames(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Hungry Flames deals 3 damage to target creature and 2 damage to target player.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new HungryFlamesEffect());
    }

    public HungryFlames(final HungryFlames card) {
        super(card);
    }

    @Override
    public HungryFlames copy() {
        return new HungryFlames(this);
    }

    private static class HungryFlamesEffect extends OneShotEffect {
        HungryFlamesEffect() {
            super(Outcome.Damage);
            this.staticText = "{this} deals 3 damage to target creature and 2 damage to target player";
        }

        HungryFlamesEffect(final HungryFlamesEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent permanent = game.getPermanent(source.getTargets().get(0).getFirstTarget());
            Player player = game.getPlayer(source.getTargets().get(1).getFirstTarget());

            if (permanent != null) {
                permanent.damage(3, source.getSourceId(), game, false, true);
            }

            if (player != null) {
                player.damage(2, source.getSourceId(), game, false, true);
            }

            return true;
        }

        @Override
        public HungryFlamesEffect copy() {
            return new HungryFlamesEffect(this);
        }
    }

}
