package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.DiesOneOrMoreCreatureTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class VengefulTownsfolk extends CardImpl {

    public static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other creatures you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public VengefulTownsfolk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever one or more other creatures you control die, put a +1/+1 counter on Vengeful Townsfolk.
        this.addAbility(new DiesOneOrMoreCreatureTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)),
                filter
        ));
    }

    private VengefulTownsfolk(final VengefulTownsfolk card) {
        super(card);
    }

    @Override
    public VengefulTownsfolk copy() {
        return new VengefulTownsfolk(this);
    }
}
