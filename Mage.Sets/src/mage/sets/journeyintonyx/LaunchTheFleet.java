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
package mage.sets.journeyintonyx;

import java.util.UUID;
import mage.abilities.abilityword.StriveAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.game.permanent.token.SoldierToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class LaunchTheFleet extends CardImpl<LaunchTheFleet> {

    public LaunchTheFleet(UUID ownerId) {
        super(ownerId, 15, "Launch the Fleet", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{W}");
        this.expansionSetCode = "JOU";

        this.color.setWhite(true);

        // Strive - Launch the Fleet costs 1 more to cast for each target beyond the first.
        this.addAbility(new StriveAbility("{1}"));
        
        // Until end of turn, any number of target creatures each gain "Whenever this creature attacks, put a 1/1 white Soldier token onto the battlefield tapped and attacking."
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, Integer.MAX_VALUE));
        Effect effect = new GainAbilityTargetEffect(new AttacksTriggeredAbility(new CreateTokenEffect(new SoldierToken(), 1, true, true), false), Duration.EndOfTurn);
        effect.setText("Until end of turn, any number of target creatures each gain \"Whenever this creature attacks, put a 1/1 white Soldier token onto the battlefield tapped and attacking.\"");
        this.getSpellAbility().addEffect(effect);
                
    }

    public LaunchTheFleet(final LaunchTheFleet card) {
        super(card);
    }

    @Override
    public LaunchTheFleet copy() {
        return new LaunchTheFleet(this);
    }
}
