package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.DiesThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PanickedBystander extends TransformingDoubleFacedCard {

    private static final Condition condition = new YouGainedLifeCondition(ComparisonType.MORE_THAN, 2);

    public PanickedBystander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.PEASANT}, "{1}{W}",
                "Cackling Culprit",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.ROGUE}, "B"
        );

        // Panicked Bystander
        this.getLeftHalfCard().setPT(2, 2);

        // Whenever Panicked Bystander or another creature you control dies, you gain 1 life.
        this.getLeftHalfCard().addAbility(new DiesThisOrAnotherTriggeredAbility(
                new GainLifeEffect(1), false, StaticFilters.FILTER_CONTROLLED_CREATURE
        ));

        // At the beginning of your end step, if you gained 3 or more life this turn, transform Panicked Bystander.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                TargetController.YOU, new TransformSourceEffect(),
                false, condition
        ).addHint(ControllerGainedLifeCount.getHint());
        ability.addWatcher(new PlayerGainedLifeWatcher());
        this.getLeftHalfCard().addAbility(ability);

        // Cackling Culprit
        this.getRightHalfCard().setPT(3, 5);

        // Whenever Cackling Culprit or another creature you control dies, you gain 1 life.
        this.getRightHalfCard().addAbility(new DiesThisOrAnotherTriggeredAbility(
                new GainLifeEffect(1), false, StaticFilters.FILTER_CONTROLLED_CREATURE
        ));

        // {1}{B}: Cackling Culprit gains deathtouch until end of turn.
        this.getRightHalfCard().addAbility(new SimpleActivatedAbility(new GainAbilitySourceEffect(
                DeathtouchAbility.getInstance(), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{1}{B}")));
    }

    private PanickedBystander(final PanickedBystander card) {
        super(card);
    }

    @Override
    public PanickedBystander copy() {
        return new PanickedBystander(this);
    }
}
