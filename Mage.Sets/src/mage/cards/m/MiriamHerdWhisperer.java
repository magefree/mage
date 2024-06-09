package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MiriamHerdWhisperer extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("a Mount or Vehicle you control");

    static {
        filter.add(Predicates.or(
                SubType.MOUNT.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }

    public MiriamHerdWhisperer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // As long as it's your turn, Mounts and Vehicles you control have hexproof.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilityAllEffect(HexproofAbility.getInstance(), Duration.WhileOnBattlefield, filter),
                MyTurnCondition.instance, "as long as it's your turn, Mounts and Vehicles you control have hexproof"
        )));

        // Whenever a Mount or Vehicle you control attacks, put a +1/+1 counter on it.
        this.addAbility(new AttacksCreatureYouControlTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                        .setText("put a +1/+1 counter on it"),
                false, filter, true
        ));
    }

    private MiriamHerdWhisperer(final MiriamHerdWhisperer card) {
        super(card);
    }

    @Override
    public MiriamHerdWhisperer copy() {
        return new MiriamHerdWhisperer(this);
    }
}
