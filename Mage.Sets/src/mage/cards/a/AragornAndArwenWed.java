package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AragornAndArwenWed extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("other creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, 1);
    private static final Hint hint = new ValueHint("Other creatures you control", xValue);

    public AragornAndArwenWed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Aragorn and Arwen, Wed enters the battlefield or attacks, put a +1/+1 counter on each other creature you control. You gain 1 life for each other creature you control.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter)
        );
        ability.addEffect(new GainLifeEffect(xValue).setText("you gain 1 life for each other creature you control"));
        ability.addHint(hint);
        this.addAbility(ability);
    }

    private AragornAndArwenWed(final AragornAndArwenWed card) {
        super(card);
    }

    @Override
    public AragornAndArwenWed copy() {
        return new AragornAndArwenWed(this);
    }
}
