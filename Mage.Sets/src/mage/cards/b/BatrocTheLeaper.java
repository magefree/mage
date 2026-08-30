package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.MultikickerCount;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MultikickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.target.common.TargetAnyTarget;
import mage.target.targetadjustment.TargetsCountAdjuster;

import java.util.UUID;

/**
 * @author muz
 */
public final class BatrocTheLeaper extends CardImpl {

    public BatrocTheLeaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Multikicker {2}
        this.addAbility(new MultikickerAbility("{2}"));

        // Batroc enters with a +1/+1 counter on him for each time he was kicked.
        this.addAbility(new EntersBattlefieldAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance(0), MultikickerCount.instance, true),
            false, null,
            "Batroc enters with a +1/+1 counter on him for each time he was kicked.",
            null
        ));

        // When Batroc enters, he deals damage equal to his power to each of up to X targets, where X is the number of times he was kicked.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(
            SourcePermanentPowerValue.NOT_NEGATIVE, "he"
        ).setText("he deals damage equal to his power to each of up to X targets, where X is the number of times he was kicked"));
        ability.addTarget(new TargetAnyTarget(0, 1));
        ability.setTargetAdjuster(new TargetsCountAdjuster(MultikickerCount.instance));
        this.addAbility(ability);
    }

    private BatrocTheLeaper(final BatrocTheLeaper card) {
        super(card);
    }

    @Override
    public BatrocTheLeaper copy() {
        return new BatrocTheLeaper(this);
    }
}
