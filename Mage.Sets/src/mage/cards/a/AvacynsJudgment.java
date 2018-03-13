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
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.target.common.TargetCreatureOrPlayerAmount;

/**
 *
 * @author LevelX2
 */
public class AvacynsJudgment extends CardImpl {

    public AvacynsJudgment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}");

        // Madness {X}{R}
        Ability ability = new MadnessAbility(this, new ManaCostsImpl("{X}{R}"));
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // Avacyn's Judgment deals 2 damage divided as you choose among any number of target creatures and/or players. If Avacyn's Judgment's madness cost was paid, it deals X damage divided as you choose among those creatures and/or players instead.
        DynamicValue xValue = new AvacynsJudgmentManacostVariableValue();
        Effect effect = new DamageMultiEffect(xValue);
        effect.setText("{this} deals 2 damage divided as you choose among any number of target creatures and/or players. If {this}'s madness cost was paid, it deals X damage divided as you choose among those creatures and/or players instead.");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayerAmount(xValue));
    }

    public AvacynsJudgment(final AvacynsJudgment card) {
        super(card);
    }

    @Override
    public AvacynsJudgment copy() {
        return new AvacynsJudgment(this);
    }
}

class AvacynsJudgmentManacostVariableValue implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        ManaCosts manaCosts = sourceAbility.getManaCostsToPay();
        if (manaCosts.getVariableCosts().isEmpty()) {
            return 2;
        }
        return sourceAbility.getManaCostsToPay().getX();
    }

    @Override
    public AvacynsJudgmentManacostVariableValue copy() {
        return new AvacynsJudgmentManacostVariableValue();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "";
    }
}
