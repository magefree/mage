package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class PowerPlantWorker extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();
    private static final FilterPermanent filter2 = new FilterControlledCreaturePermanent();

    static {
        filter.add(new NamePredicate("Mine Worker"));
        filter2.add(new NamePredicate("Tower Worker"));
    }

    private static final Condition condition = new CompoundCondition(
            new PermanentsOnTheBattlefieldCondition(filter),
            new PermanentsOnTheBattlefieldCondition(filter2)
    );

    public PowerPlantWorker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");

        this.subtype.add(SubType.ASSEMBLY_WORKER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {3}: Power Plant Worker gets +2/+2 until end of turn. If you control creatures named Mine Worker and Tower Worker, put two +1/+1 counters on Power Plant Worker instead. Activate only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(
                Zone.BATTLEFIELD,
                new ConditionalOneShotEffect(
                        new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
                        new AddContinuousEffectToGame(new BoostSourceEffect(2, 2, Duration.EndOfTurn)),
                        condition, "{this} gets +2/+2 until end of turn. If you control creatures " +
                        "named Mine Worker and Tower Worker, put two +1/+1 counters on {this} instead"
                ), new GenericManaCost(3)
        ));
    }

    private PowerPlantWorker(final PowerPlantWorker card) {
        super(card);
    }

    @Override
    public PowerPlantWorker copy() {
        return new PowerPlantWorker(this);
    }
}
