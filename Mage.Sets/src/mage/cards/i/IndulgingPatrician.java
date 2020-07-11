package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IndulgingPatrician extends CardImpl {

    private static final Condition condition = new YouGainedLifeCondition(ComparisonType.MORE_THAN, 2);
    private static final Hint hint = new ConditionHint(condition, "You gained 3 or more life this turn");

    public IndulgingPatrician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // At the beginning of your end step, if you gained 3 or more life this turn, each opponent loses 3 life.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(
                        new LoseLifeOpponentsEffect(3), TargetController.YOU, false
                ), condition, "At the beginning of your end step, " +
                "if you gained 3 or more life this turn, each opponent loses 3 life."
        ).addHint(hint), new PlayerGainedLifeWatcher());
    }

    private IndulgingPatrician(final IndulgingPatrician card) {
        super(card);
    }

    @Override
    public IndulgingPatrician copy() {
        return new IndulgingPatrician(this);
    }
}
