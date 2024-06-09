package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.MultikickerCount;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MultikickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlametongueYearling extends CardImpl {

    private static final DynamicValue xValue = new SourcePermanentPowerCount(false);

    public FlametongueYearling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{R}");

        this.subtype.add(SubType.KAVU);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Multikicker {2}
        this.addAbility(new MultikickerAbility("{2}"));

        // Flametongue Yearling enters the battlefield with a +1/+1 counter on it for each time it was kicked.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(0), MultikickerCount.instance, true
        ), "with a +1/+1 counter on it for each time it was kicked"));

        // When Flametongue Yearling enters the battlefield, it deals damage equal to its power to target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(xValue)
                .setText("it deals damage equal to its power to target creature"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private FlametongueYearling(final FlametongueYearling card) {
        super(card);
    }

    @Override
    public FlametongueYearling copy() {
        return new FlametongueYearling(this);
    }
}
