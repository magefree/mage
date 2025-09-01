package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.DiesThisOrAnotherTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PanickedBystander extends CardImpl {

    private static final Condition condition = new YouGainedLifeCondition(ComparisonType.MORE_THAN, 2);

    public PanickedBystander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.secondSideCardClazz = mage.cards.c.CacklingCulprit.class;

        // Whenever Panicked Bystander or another creature you control dies, you gain 1 life.
        this.addAbility(new DiesThisOrAnotherTriggeredAbility(
                new GainLifeEffect(1), false, StaticFilters.FILTER_CONTROLLED_CREATURE
        ));

        // At the beginning of your end step, if you gained 3 or more life this turn, transform Panicked Bystander.
        this.addAbility(new TransformAbility());
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.YOU, new TransformSourceEffect(),
                false, condition
        ).addHint(ControllerGainedLifeCount.getHint()), new PlayerGainedLifeWatcher());
    }

    private PanickedBystander(final PanickedBystander card) {
        super(card);
    }

    @Override
    public PanickedBystander copy() {
        return new PanickedBystander(this);
    }
}
