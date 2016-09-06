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
package mage.sets.weatherlight;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Dilnu
 */
public class DebtOfLoyalty extends CardImpl {

    public DebtOfLoyalty(UUID ownerId) {
        super(ownerId, 127, "Debt of Loyalty", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{1}{W}{W}");
        this.expansionSetCode = "WTH";

        // Regenerate target creature. You gain control of that creature if it regenerates this way.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new DebtOfLoyaltyEffect());
    }

    public DebtOfLoyalty(final DebtOfLoyalty card) {
        super(card);
    }

    @Override
    public DebtOfLoyalty copy() {
        return new DebtOfLoyalty(this);
    }
    
    class DebtOfLoyaltyEffect extends RegenerateTargetEffect {
        public DebtOfLoyaltyEffect ( ) {
            super();
            this.staticText = "Regenerate target creature. You gain control of that creature if it regenerates this way.";
        }

        public DebtOfLoyaltyEffect(final DebtOfLoyaltyEffect effect) {
            super(effect);
        }
        
        @Override
        public DebtOfLoyaltyEffect copy() {
            return new DebtOfLoyaltyEffect(this);
        }
        
        @Override
        public boolean apply(Game game, Ability source) {
            Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
            if (super.apply(game, source) && permanent != null) {
                GainControlTargetEffect effect = new GainControlTargetEffect(Duration.EndOfGame);
                effect.setTargetPointer(targetPointer);
                game.addEffect(effect, source);
                return true;
            }
            return false;
        }
    }
}
