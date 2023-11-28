package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.target.common.TargetControlledPermanent;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

/**
 * @author xenohedron
 */
public final class WardenOfTheInnerSky extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped artifacts and/or creatures you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public WardenOfTheInnerSky(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // As long as Warden of the Inner Sky has three or more counters on it, it has flying and vigilance.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield),
                WardenOfTheInnerSkyCondition.instance,
                "as long as {this} has three or more counters on it, it has flying"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(VigilanceAbility.getInstance(), Duration.WhileOnBattlefield),
                WardenOfTheInnerSkyCondition.instance, "and vigilance"
        ));
        this.addAbility(ability);

        // Tap three untapped artifacts and/or creatures you control: Put a +1/+1 counter on Warden of the Inner Sky. Scry 1. Activate only as a sorcery.
        Ability activated = new ActivateAsSorceryActivatedAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                new TapTargetCost(new TargetControlledPermanent(3, filter)));
        activated.addEffect(new ScryEffect(1, false));
        this.addAbility(activated);

    }

    private WardenOfTheInnerSky(final WardenOfTheInnerSky card) {
        super(card);
    }

    @Override
    public WardenOfTheInnerSky copy() {
        return new WardenOfTheInnerSky(this);
    }
}

enum WardenOfTheInnerSkyCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional.ofNullable(source.getSourcePermanentIfItStillExists(game))
                .map(permanent -> permanent.getCounters(game))
                .map(HashMap::values)
                .map(Collection::stream)
                .map(x -> x.mapToInt(Counter::getCount))
                .map(IntStream::sum)
                .orElse(0) >= 3;
    }
}
