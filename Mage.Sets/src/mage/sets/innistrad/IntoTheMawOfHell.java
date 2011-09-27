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
package mage.sets.innistrad;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author North
 */
public class IntoTheMawOfHell extends CardImpl<IntoTheMawOfHell> {

    public IntoTheMawOfHell(UUID ownerId) {
        super(ownerId, 150, "Into the Maw of Hell", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{4}{R}{R}");
        this.expansionSetCode = "ISD";

        this.color.setRed(true);

        // Destroy target land. Into the Maw of Hell deals 13 damage to target creature.
        this.getSpellAbility().addTarget(new TargetLandPermanent());
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new IntoTheMawOfHellEffect());
    }

    public IntoTheMawOfHell(final IntoTheMawOfHell card) {
        super(card);
    }

    @Override
    public IntoTheMawOfHell copy() {
        return new IntoTheMawOfHell(this);
    }
}

class IntoTheMawOfHellEffect extends OneShotEffect<IntoTheMawOfHellEffect> {

    public IntoTheMawOfHellEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 13 damage to target creature";
    }

    public IntoTheMawOfHellEffect(final IntoTheMawOfHellEffect effect) {
        super(effect);
    }

    @Override
    public IntoTheMawOfHellEffect copy() {
        return new IntoTheMawOfHellEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (permanent != null) {
            permanent.damage(13, source.getSourceId(), game, true, false);
            return true;
        }
        return false;
    }
}
