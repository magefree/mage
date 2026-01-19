package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.CrewedSourceThisTurnPredicate;
import mage.target.TargetPermanent;
import mage.watchers.common.CrewedVehicleWatcher;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.SourceHasSubtypeCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DoubleCountersTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class TurtleVan extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature that crewed it this turn");

    static {
        filter.add(CrewedSourceThisTurnPredicate.instance);
    }

    public TurtleVan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever this Vehicle attacks, put a +1/+1 counter on target creature that crewed it this turn. Then if that creature is a Mutant, Ninja, or Turtle, double the number of +1/+1 counters on it.
        Ability ability = new AttacksTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetPermanent(filter));
        ability.addEffect(new ConditionalOneShotEffect(
            new DoubleCountersTargetEffect(CounterType.P1P1).setText(", then double the number of +1/+1 counters on it"),
            new SourceHasSubtypeCondition(SubType.MUTANT, SubType.NINJA, SubType.TURTLE)
        ).concatBy("Then"));
        this.addAbility(ability, new CrewedVehicleWatcher());

        // Crew 1
        this.addAbility(new CrewAbility(1));

    }

    private TurtleVan(final TurtleVan card) {
        super(card);
    }

    @Override
    public TurtleVan copy() {
        return new TurtleVan(this);
    }
}
