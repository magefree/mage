package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AvengingHuntbonder extends CardImpl {

    private static final FilterPermanent filter
            = new FilterAttackingCreature("another target attacking creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public AvengingHuntbonder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Whenever Avenging Huntbonder attacks, put a double strike counter on another target attacking creature.
        Ability ability = new AttacksTriggeredAbility(
                new AddCountersTargetEffect(CounterType.DOUBLE_STRIKE.createInstance()), false
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private AvengingHuntbonder(final AvengingHuntbonder card) {
        super(card);
    }

    @Override
    public AvengingHuntbonder copy() {
        return new AvengingHuntbonder(this);
    }
}
