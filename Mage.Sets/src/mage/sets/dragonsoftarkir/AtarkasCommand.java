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
package mage.sets.dragonsoftarkir;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.PutLandFromHandOntoBattlefieldEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.CantGainLifeAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.TargetController;

/**
 *
 * @author LevelX2
 */
public class AtarkasCommand extends CardImpl {

    public AtarkasCommand(UUID ownerId) {
        super(ownerId, 213, "Atarka's Command", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{R}{G}");
        this.expansionSetCode = "DTK";

        // Choose two - 
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);
        
        // Your opponents can't gain life this turn; 
        this.getSpellAbility().addEffect(new CantGainLifeAllEffect(Duration.EndOfTurn, TargetController.OPPONENT));
        
        // or Atarka's Command deals 3 damage to each opponent; 
        Mode mode = new Mode();
        mode.getEffects().add(new DamagePlayersEffect(3, TargetController.OPPONENT));
        this.getSpellAbility().addMode(mode);
        
        // or You may put a land card from your hand onto the battlefield; 
        mode = new Mode();
        mode.getEffects().add(new PutLandFromHandOntoBattlefieldEffect());
        this.getSpellAbility().addMode(mode);

        // or Creatures you control get +1/+1 and gain reach until the end of turn.
        mode = new Mode();
        Effect effect = new BoostControlledEffect(1,1, Duration.EndOfTurn);
        effect.setText("Creatures you control get +1/+1");        
        mode.getEffects().add(effect);
        effect = new GainAbilityControlledEffect(ReachAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gain reach until the end of turn");        
        mode.getEffects().add(effect);
        this.getSpellAbility().addMode(mode);
        
    }

    public AtarkasCommand(final AtarkasCommand card) {
        super(card);
    }

    @Override
    public AtarkasCommand copy() {
        return new AtarkasCommand(this);
    }
}
