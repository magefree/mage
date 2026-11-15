package mage.cards.r;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author muz
 */
public final class RomulanBirdOfPrey extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();
    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }
    private static final Condition condition = new SourceMatchesFilterCondition("{this} isn't a creature", filter);

    public RomulanBirdOfPrey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{U}");

        this.subtype.add(SubType.SPACECRAFT);

        // Cloaking Device -- As long as this Spacecraft isn't a creature, it has hexproof.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
            new GainAbilitySourceEffect(HexproofAbility.getInstance()), condition,
            "as long as {this} isn't a creature, it has hexproof"
        )).withFlavorWord("Cloaking Device"));

        // Whenever you cast a noncreature spell, if this Spacecraft isn't a creature, put two charge counters on it.
        this.addAbility(new SpellCastControllerTriggeredAbility(
            new AddCountersSourceEffect(CounterType.CHARGE.createInstance(2)), StaticFilters.FILTER_SPELL_NON_CREATURE, false
        ).withInterveningIf(condition));

        // Station
        this.addAbility(new StationAbility());

        // STATION 8+
        // Flying
        // 4/4
        this.addAbility(new StationLevelAbility(8).withLevelAbility(FlyingAbility.getInstance()).withPT(4, 4));
    }

    private RomulanBirdOfPrey(final RomulanBirdOfPrey card) {
        super(card);
    }

    @Override
    public RomulanBirdOfPrey copy() {
        return new RomulanBirdOfPrey(this);
    }
}
