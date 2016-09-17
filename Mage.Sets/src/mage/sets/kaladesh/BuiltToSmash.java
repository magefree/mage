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
package mage.sets.kaladesh;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;



/**
 *
 * @author spjspj
 */
public class BuiltToSmash extends CardImpl {

    public BuiltToSmash(UUID ownerId) {
        super(ownerId, 108, "Built to Smash", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{R}");
        this.expansionSetCode = "KLD";

        // Target attacking creature gets +3/+3 until end of turn/...
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 3, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(new FilterAttackingCreature())); 
        // ... If it's an artifact creature, it gains trample until end of turn.
        this.getSpellAbility().addEffect(new BuiltToSmashTrampleEffect());
    }

    public BuiltToSmash(final BuiltToSmash card) {
        super(card);
    }

    @Override
    public BuiltToSmash copy() {
        return new BuiltToSmash(this);
    }

    private class BuiltToSmashTrampleEffect extends ContinuousEffectImpl {

        public BuiltToSmashTrampleEffect() {
            super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
            staticText = "If it's an Artifact, it gains trample until end of turn";
        }

        public BuiltToSmashTrampleEffect(final BuiltToSmashTrampleEffect effect) {
            super(effect);
        }

        @Override
        public BuiltToSmashTrampleEffect copy() {
            return new BuiltToSmashTrampleEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            int affectedTargets = 0;
            for (UUID permanentId : targetPointer.getTargets(game, source)) {
                Permanent permanent = game.getPermanent(permanentId);
                if (permanent != null && permanent.hasSubtype("Artifact", game)) {
                    permanent.addAbility(TrampleAbility.getInstance(), source.getSourceId(), game);
                    affectedTargets++;
                }
            }
            return affectedTargets > 0;
        }

    } 
}
