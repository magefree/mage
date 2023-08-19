package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.ProtectionFromEverythingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheOneRing extends CardImpl {

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.BURDEN);

    public TheOneRing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        this.supertype.add(SuperType.LEGENDARY);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // When The One Ring enters the battlefield, if you cast it, you gain protection from everything until your next turn.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new GainAbilityControllerEffect(
                        new ProtectionFromEverythingAbility(), Duration.UntilYourNextTurn
                )), CastFromEverywhereSourceCondition.instance, "When {this} enters the battlefield, " +
                "if you cast it, you gain protection from everything until your next turn."
        ));

        // At the beginning of your upkeep, you lose 1 life for each burden counter on The One Ring.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new LoseLifeSourceControllerEffect(xValue), TargetController.YOU, false
        ));

        // {T}: Put a burden counter on The One Ring, then draw a card for each burden counter on The One Ring.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.BURDEN.createInstance()), new TapSourceCost()
        );
        ability.addEffect(new DrawCardSourceControllerEffect(xValue).concatBy(", then"));
        this.addAbility(ability);
    }

    private TheOneRing(final TheOneRing card) {
        super(card);
    }

    @Override
    public TheOneRing copy() {
        return new TheOneRing(this);
    }
}
