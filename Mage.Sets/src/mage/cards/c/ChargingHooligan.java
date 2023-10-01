package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.permanent.AttackingPredicate;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ChargingHooligan extends CardImpl {

    private static final DynamicValue xValue =
            new PermanentsOnBattlefieldCount(StaticFilters.FILTER_ATTACKING_CREATURE);

    private static final FilterPermanent filterForCondition = new FilterPermanent(SubType.RAT, "a Rat is attacking");

    static {
        filterForCondition.add(AttackingPredicate.instance);
    }

    private static final Condition condition =
            new PermanentsOnTheBattlefieldCondition(
                    filterForCondition, ComparisonType.MORE_THAN,
                    0, false
            );

    public ChargingHooligan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Charging Hooligan attacks, it gets +1/+0 until end of turn for each attacking creature. If a Rat is attacking, Charging Hooligan gains trample until end of turn.
        Ability ability = new AttacksTriggeredAbility(
                new BoostSourceEffect(xValue, StaticValue.get(0), Duration.EndOfTurn, "it")
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(
                        new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.EndOfTurn)
                ), condition
        ));

        this.addAbility(ability);
    }

    private ChargingHooligan(final ChargingHooligan card) {
        super(card);
    }

    @Override
    public ChargingHooligan copy() {
        return new ChargingHooligan(this);
    }
}
