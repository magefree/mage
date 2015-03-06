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
package mage.sets.fatereforged;

import java.util.UUID;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public class TemurBattleRage extends CardImpl {
    
    private final static String rule = "<br><i>Ferocious</i> &mdash; That creature also gains trample until end of turn if you control a creature with power 4 or greater";

    public TemurBattleRage(UUID ownerId) {
        super(ownerId, 116, "Temur Battle Rage", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{R}");
        this.expansionSetCode = "FRF";

        // Target creature gains double strike until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        
        // <i>Ferocious</i> That creature also gains trample until end of turn if you control a creature with power 4 or greater.
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(
                new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn),
                new LockedInCondition(FerociousCondition.getInstance()),
                rule));
        
    }

    public TemurBattleRage(final TemurBattleRage card) {
        super(card);
    }

    @Override
    public TemurBattleRage copy() {
        return new TemurBattleRage(this);
    }
}
