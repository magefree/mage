package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.CrewAbility;
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
public final class GoliathTruck extends CardImpl {

    private static final FilterPermanent filter = new FilterAttackingCreature("another target attacking creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public GoliathTruck(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Stowage -- Whenever Goliath Truck attacks, put two +1/+1 counters on another target attacking creature.
        Ability ability = new AttacksTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability.withFlavorWord("Stowage"));

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private GoliathTruck(final GoliathTruck card) {
        super(card);
    }

    @Override
    public GoliathTruck copy() {
        return new GoliathTruck(this);
    }
}
