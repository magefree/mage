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
package mage.sets.avacynrestored;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author noxx
 */
public class JointAssault extends CardImpl<JointAssault> {

    public JointAssault(UUID ownerId) {
        super(ownerId, 183, "Joint Assault", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{G}");
        this.expansionSetCode = "AVR";

        this.color.setGreen(true);

        // Target creature gets +2/+2 until end of turn. If it's paired with a creature, that creature also gets +2/+2 until end of turn.
        this.getSpellAbility().addEffect(new JointAssaultBoostTargetEffect(2, 2, Constants.Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public JointAssault(final JointAssault card) {
        super(card);
    }

    @Override
    public JointAssault copy() {
        return new JointAssault(this);
    }
}

class JointAssaultBoostTargetEffect extends ContinuousEffectImpl<JointAssaultBoostTargetEffect> {

    private int power;
    private int toughness;
    private UUID paired;

    public JointAssaultBoostTargetEffect(int power, int toughness, Constants.Duration duration) {
        super(duration, Constants.Layer.PTChangingEffects_7, Constants.SubLayer.ModifyPT_7c, Constants.Outcome.BoostCreature);
        this.power = power;
        this.toughness = toughness;
        staticText = "Target creature gets +2/+2 until end of turn. If it's paired with a creature, that creature also gets +2/+2 until end of turn";
    }

    public JointAssaultBoostTargetEffect(final JointAssaultBoostTargetEffect effect) {
        super(effect);
        this.power = effect.power;
        this.toughness = effect.toughness;
    }

    @Override
    public JointAssaultBoostTargetEffect copy() {
        return new JointAssaultBoostTargetEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        UUID permanentId = targetPointer.getFirst(game, source);
        Permanent target = game.getPermanent(permanentId);
        if (target != null) {
            if (target.getPairedCard() != null) {
                this.paired = target.getPairedCard();
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int affectedTargets = 0;
        UUID permanentId = targetPointer.getFirst(game, source);

        Permanent target = game.getPermanent(permanentId);
        if (target != null) {
            target.addPower(power);
            target.addToughness(toughness);
            affectedTargets++;
        }

        if (this.paired != null) {
            Permanent paired = game.getPermanent(this.paired);
            if (paired != null) {
                paired.addPower(power);
                paired.addToughness(toughness);
                affectedTargets++;
            }
        }

        return affectedTargets > 0;
    }
}
