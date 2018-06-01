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
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterTeamCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class ComboAttack extends CardImpl {

    public ComboAttack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Two target creatures your team controls each deal damage equal to their power to target creature.
        this.getSpellAbility().addEffect(new ComboAttackEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(2, 2, new FilterTeamCreaturePermanent(), false));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(1));
    }

    public ComboAttack(final ComboAttack card) {
        super(card);
    }

    @Override
    public ComboAttack copy() {
        return new ComboAttack(this);
    }
}

class ComboAttackEffect extends OneShotEffect {

    ComboAttackEffect() {
        super(Outcome.Benefit);
        this.staticText = "Two target creatures your team controls each deal damage equal to their power to target creature";
    }

    ComboAttackEffect(final ComboAttackEffect effect) {
        super(effect);
    }

    @Override
    public ComboAttackEffect copy() {
        return new ComboAttackEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent1 = game.getPermanent(source.getTargets().get(0).getTargets().get(0));
        Permanent permanent2 = game.getPermanent(source.getTargets().get(0).getTargets().get(1));
        Permanent permanent3 = game.getPermanent(source.getTargets().get(1).getTargets().get(0));
        if (permanent3 == null) {
            return false;
        }
        if (permanent1 != null) {
            permanent3.damage(permanent1.getPower().getValue(), permanent1.getId(), game, false, true);
        }
        if (permanent2 != null) {
            permanent3.damage(permanent2.getPower().getValue(), permanent2.getId(), game, false, true);
        }
        return true;
    }
}
