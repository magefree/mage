package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.dynamicvalue.common.SourcePermanentToughnessValue;
import mage.abilities.effects.common.DoubleCountersTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TanazirQuandrix extends CardImpl {

    private static final DynamicValue xValue = new SourcePermanentPowerCount(true);

    public TanazirQuandrix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Tanazir Quandrix enters the battlefield, double the number of +1/+1 counters on target creature you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DoubleCountersTargetEffect(CounterType.P1P1)
                .setText("double the number of +1/+1 counters on target creature you control")
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Whenever Tanazir Quandrix attacks, you may have the base power and toughness of other creatures you control become equal to Tanazir Quandrix's power and toughness until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new SetBasePowerToughnessAllEffect(
                xValue, SourcePermanentToughnessValue.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE
        ).setText("have the base power and toughness of other creatures you control " +
                "become equal to {this}'s power and toughness until end of turn"), true));
    }

    private TanazirQuandrix(final TanazirQuandrix card) {
        super(card);
    }

    @Override
    public TanazirQuandrix copy() {
        return new TanazirQuandrix(this);
    }
}
