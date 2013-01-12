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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class UncheckedGrowth extends CardImpl<UncheckedGrowth> {

    public UncheckedGrowth(UUID ownerId) {
        super(ownerId, 148, "Unchecked Growth", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{2}{G}");
        this.expansionSetCode = "BOK";
        this.subtype.add("Arcane");
        this.color.setGreen(true);
        
        // Target creature gets +4/+4 until end of turn. 
        this.getSpellAbility().addEffect(new BoostTargetEffect(4, 4, Constants.Duration.EndOfTurn));
        // If it's a Spirit, it gains trample until end of turn.
        this.getSpellAbility().addEffect(new UncheckedGrowthTrampleEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public UncheckedGrowth(final UncheckedGrowth card) {
        super(card);
    }

    @Override
    public UncheckedGrowth copy() {
        return new UncheckedGrowth(this);
    }
    
    private class UncheckedGrowthTrampleEffect extends ContinuousEffectImpl<UncheckedGrowthTrampleEffect> {

        public UncheckedGrowthTrampleEffect() {
            super(Constants.Duration.EndOfTurn, Constants.Layer.AbilityAddingRemovingEffects_6, Constants.SubLayer.NA, Constants.Outcome.AddAbility);
            staticText = "If it's a Spirit, it gains trample until end of turn";
        }

        public UncheckedGrowthTrampleEffect(final UncheckedGrowthTrampleEffect effect) {
            super(effect);
        }

        @Override
        public UncheckedGrowthTrampleEffect copy() {
            return new UncheckedGrowthTrampleEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            int affectedTargets = 0;
            for (UUID permanentId : targetPointer.getTargets(game, source)) {
                Permanent permanent = game.getPermanent(permanentId);
                if (permanent != null && permanent.hasSubtype("Spirit")) {
                    permanent.addAbility(TrampleAbility.getInstance(), game);
                    affectedTargets++;
                }
            }
            return affectedTargets > 0;
        }

    }
}
