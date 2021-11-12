package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HalanaAndAlenaPartners extends CardImpl {

    private static final DynamicValue xValue = new SourcePermanentPowerCount();

    public HalanaAndAlenaPartners(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // At the beginning of combat on your turn, put X +1/+1 counters on another target creature you control, where X is Halana and Alena's power. That creature gains haste until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(0), xValue)
                        .setText("put X +1/+1 counters on another target creature you control, where X is {this}'s power"),
                TargetController.YOU, false
        );
        ability.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("That creature gains haste until end of turn"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));
        this.addAbility(ability);
    }

    private HalanaAndAlenaPartners(final HalanaAndAlenaPartners card) {
        super(card);
    }

    @Override
    public HalanaAndAlenaPartners copy() {
        return new HalanaAndAlenaPartners(this);
    }
}
// oh my god they were roommates
