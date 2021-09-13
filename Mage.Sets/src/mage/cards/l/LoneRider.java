package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class LoneRider extends CardImpl {

    private static final String ruleText = "At the beginning of the end step, if you gained 3 or more life this turn, transform {this}";
    private static final Condition condition = new YouGainedLifeCondition(ComparisonType.MORE_THAN, 2);
    private static final Hint hint = new ConditionHint(condition, "You gained 3 or more life this turn");

    public LoneRider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.secondSideCardClazz = mage.cards.i.ItThatRidesAsOne.class;

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // At the beginning of the end step, if you gained 3 or more life this turn, transform Lone Rider.
        this.addAbility(new TransformAbility());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(
                        new TransformSourceEffect(), TargetController.NEXT, false
                ), condition, ruleText
        ).addHint(hint), new PlayerGainedLifeWatcher());
    }

    private LoneRider(final LoneRider card) {
        super(card);
    }

    @Override
    public LoneRider copy() {
        return new LoneRider(this);
    }
}
