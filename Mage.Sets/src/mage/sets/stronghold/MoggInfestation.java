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
package mage.sets.stronghold;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.GoblinToken;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public class MoggInfestation extends CardImpl {

    public MoggInfestation(UUID ownerId) {
        super(ownerId, 93, "Mogg Infestation", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");
        this.expansionSetCode = "STH";

        // Destroy all creatures target player controls. For each creature that died this way, put two 1/1 red Goblin creature tokens onto the battlefield under that player's control.
        getSpellAbility().addTarget(new TargetPlayer());
        getSpellAbility().addEffect(new MoggInfestationEffect());

    }

    public MoggInfestation(final MoggInfestation card) {
        super(card);
    }

    @Override
    public MoggInfestation copy() {
        return new MoggInfestation(this);
    }
}

class MoggInfestationEffect extends OneShotEffect {

    public MoggInfestationEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy all creatures target player controls. For each creature that died this way, put two 1/1 red Goblin creature tokens onto the battlefield under that player's control";
    }

    public MoggInfestationEffect(final MoggInfestationEffect effect) {
        super(effect);
    }

    @Override
    public MoggInfestationEffect copy() {
       return new MoggInfestationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && getTargetPointer().getFirst(game, source) != null) {
            for (Permanent permanent: game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), getTargetPointer().getFirst(game, source), game)) {
                if (permanent.destroy(source.getSourceId(), game, false)) {
                    Effect effect = new CreateTokenTargetEffect(new GoblinToken(), 2);
                    effect.setTargetPointer(getTargetPointer());
                    effect.apply(game, source);
                }
            }
            return true;
        }
        return false;
    }
}
