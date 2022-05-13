package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.EvokedCondition;
import mage.abilities.costs.AlternativeSourceCostsImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;

/**
 * @author LevelX2
 */
public class EvokeAbility extends AlternativeSourceCostsImpl {

    protected static final String EVOKE_KEYWORD = "Evoke";
    protected static final String REMINDER_TEXT = "You may cast this spell for its evoke cost. "
            + "If you do, it's sacrificed when it enters the battlefield.";

    public EvokeAbility(String manaString) {
        this(new ManaCostsImpl<>(manaString));
    }

    public EvokeAbility(Cost cost) {
        super(EVOKE_KEYWORD, REMINDER_TEXT, cost);
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new SacrificeSourceEffect()),
                EvokedCondition.instance, "Sacrifice {this} when it enters the battlefield and was evoked.");
        ability.setRuleVisible(false);
        addSubAbility(ability);
    }

    private EvokeAbility(final EvokeAbility ability) {
        super(ability);
    }

    @Override
    public EvokeAbility copy() {
        return new EvokeAbility(this);
    }
}
