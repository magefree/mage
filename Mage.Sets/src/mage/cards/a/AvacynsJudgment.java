
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
import mage.target.common.TargetAnyTargetAmount;

/**
 *
 * @author LevelX2
 */
public final class AvacynsJudgment extends CardImpl {

    public AvacynsJudgment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}");

        // Madness {X}{R}
        Ability ability = new MadnessAbility(new ManaCostsImpl("{X}{R}"));
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // Avacyn's Judgment deals 2 damage divided as you choose among any number of target creatures and/or players. If Avacyn's Judgment's madness cost was paid, it deals X damage divided as you choose among those creatures and/or players instead.
        DynamicValue xValue = new AvacynsJudgmentManacostVariableValue();
        Effect effect = new DamageMultiEffect(xValue);
        effect.setText("{this} deals 2 damage divided as you choose among any number of targets. If this spell's madness cost was paid, it deals X damage divided as you choose among those permanents and/or players instead.");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetAnyTargetAmount(xValue));
    }

    private AvacynsJudgment(final AvacynsJudgment card) {
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
