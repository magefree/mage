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
package mage.sets.apocalypse;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class ConsumeStrength extends CardImpl<ConsumeStrength> {

    public ConsumeStrength(UUID ownerId) {
        super(ownerId, 93, "Consume Strength", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{B}{G}");
        this.expansionSetCode = "APC";

        this.color.setGreen(true);
        this.color.setBlack(true);

        // Target creature gets +2/+2 until end of turn. Another target creature gets -2/-2 until end of turn.
        this.getSpellAbility().addEffect(new ConsumeStrengthEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(2, true));
        
    }

    public ConsumeStrength(final ConsumeStrength card) {
        super(card);
    }

    @Override
    public ConsumeStrength copy() {
        return new ConsumeStrength(this);
    }
}

class ConsumeStrengthEffect extends ContinuousEffectImpl<ConsumeStrengthEffect> {

    public ConsumeStrengthEffect() {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        this.staticText = "Target creature gets +2/+2 until end of turn. Another target creature gets -2/-2 until end of turn";
    }

    public ConsumeStrengthEffect(final ConsumeStrengthEffect effect) {
        super(effect);
    }

    @Override
    public ConsumeStrengthEffect copy() {
        return new ConsumeStrengthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            permanent.addPower(2);
            permanent.addToughness(2);
        }
        permanent = game.getPermanent(source.getTargets().get(0).getTargets().get(1));
        if (permanent != null) {
            permanent.addPower(-2);
            permanent.addToughness(-2);
        }
        return true;
    }
}