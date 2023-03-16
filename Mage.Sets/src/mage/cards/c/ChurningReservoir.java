package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.token.PhyrexianGoblinToken;
import mage.target.common.TargetControlledPermanent;
import mage.watchers.common.CountersWatcher;
import mage.watchers.common.PermanentsWithCountersPutIntoGraveyardWatcher;

import java.util.UUID;

public class ChurningReservoir extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another target nontoken artifact or creature you control");

    static {
        filter.add(Predicates.or(
                Predicates.and(
                        TokenPredicate.FALSE,
                        CardType.ARTIFACT.getPredicate()
                ),
                CardType.CREATURE.getPredicate()
        ));
        filter.add(AnotherPredicate.instance);
    }

    public ChurningReservoir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{R}");

        //At the beginning of your upkeep, put an oil counter on another target nontoken artifact or creature you control.
        BeginningOfUpkeepTriggeredAbility beginningOfUpkeepTriggeredAbility = new BeginningOfUpkeepTriggeredAbility(
                new AddCountersTargetEffect(CounterType.OIL.createInstance(1)), TargetController.YOU, false
        );
        beginningOfUpkeepTriggeredAbility.addTarget(new TargetControlledPermanent(filter));
        this.addAbility(beginningOfUpkeepTriggeredAbility);

        //{2}, {T}: Create a 1/1 red Phyrexian Goblin creature token. Activate only if an oil counter was removed from a
        //permanent you controlled this turn or a permanent with an oil counter on it was put into a graveyard this turn.
        ConditionalActivatedAbility conditionalActivatedAbility =new ConditionalActivatedAbility(
                Zone.BATTLEFIELD, new CreateTokenEffect(new PhyrexianGoblinToken()), new ManaCostsImpl<>("{2}"),
                new ChurningReservoirCondition()
        );
        conditionalActivatedAbility.addCost(new TapSourceCost());
        conditionalActivatedAbility.addWatcher(new CountersWatcher());
        conditionalActivatedAbility.addWatcher(new PermanentsWithCountersPutIntoGraveyardWatcher());
        this.addAbility(conditionalActivatedAbility);
    }

    private ChurningReservoir(final ChurningReservoir card) {
        super(card);
    }

    @Override
    public ChurningReservoir copy() {
        return new ChurningReservoir(this);
    }
}

class ChurningReservoirCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        CountersWatcher counterWatcher = game.getState().getWatcher(CountersWatcher.class);
        if (counterWatcher.counterTypeHasBeenRemovedFromCardControlledByPlayer(CounterType.OIL, source.getControllerId(), game)) {
            return true;
        }
        PermanentsWithCountersPutIntoGraveyardWatcher graveyardWatcher = game.getState().getWatcher(PermanentsWithCountersPutIntoGraveyardWatcher.class);
        return graveyardWatcher.permanentWithCounterWasPutIntoGraveyard(CounterType.OIL);
    }

    @Override
    public String toString() {
        return "an oil counter was removed from a permanent you controlled this turn or a permanent with an oil " +
                "counter on it was put into a graveyard this turn";
    }
}
