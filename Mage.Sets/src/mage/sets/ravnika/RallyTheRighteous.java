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

package mage.sets.ravnika;

import java.util.UUID;

import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Outcome;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.SubLayer;
import mage.game.permanent.Permanent;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.game.Game;

/**
 * @author duncancmt
 */

public class RallyTheRighteous extends CardImpl {

    public RallyTheRighteous(UUID ownerId) {
        super(ownerId, 222, "Rally the Righteous", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{R}{W}");
        this.expansionSetCode = "RAV";
        
        // Radiance — Untap target creature and each other creature that shares a color with it. Those creatures get +2/+0 until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new RallyTheRighteousUntapEffect());
        this.getSpellAbility().addEffect(new RallyTheRighteousBoostEffect());
    }

    public RallyTheRighteous(final RallyTheRighteous card) {
        super(card);
    }

    @Override
    public RallyTheRighteous copy() {
        return new RallyTheRighteous(this);
    }
}


class RallyTheRighteousUntapEffect extends OneShotEffect {

    public RallyTheRighteousUntapEffect() {
        super(Outcome.Untap);
        staticText = "<i>Radiance</i> — Untap target creature and each other creature that shares a color with it.";
    }

    public RallyTheRighteousUntapEffect(final RallyTheRighteousUntapEffect effect) {
        super(effect);
    }

    @Override
    public RallyTheRighteousUntapEffect copy() {
        return new RallyTheRighteousUntapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(targetPointer.getFirst(game, source));
        if (target != null) {
            ObjectColor color = target.getColor();
            target.untap(game);
            for (Permanent p : game.getBattlefield().getActivePermanents(new FilterCreaturePermanent(), source.getControllerId(), game)) {
                if (p.getColor().shares(color) && !p.getId().equals(target.getId())) {
                    p.untap(game);
                }
            }
            return true;
        }
        return false;
    }
}


class RallyTheRighteousBoostEffect extends ContinuousEffectImpl {

    public RallyTheRighteousBoostEffect() {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        staticText = " Those creatures get +2/+0 until end of turn.";
    }

    public RallyTheRighteousBoostEffect(final RallyTheRighteousBoostEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(targetPointer.getFirst(game, source));
        if (target != null) {
            ObjectColor color = target.getColor();
            target.addPower(2);
            for (Permanent p : game.getBattlefield().getActivePermanents(new FilterCreaturePermanent(), source.getControllerId(), game)) {
                if (p.getColor().shares(color) && !p.getId().equals(target.getId())) {
                    p.addPower(2);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public RallyTheRighteousBoostEffect copy() {
        return new RallyTheRighteousBoostEffect(this);
    }
}
