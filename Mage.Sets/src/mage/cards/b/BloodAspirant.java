package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodAspirant extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a creature or enchantment");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
    }

    public BloodAspirant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.SATYR);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you sacrifice a permanent, put a +1/+1 counter on Blood Aspirant.
        this.addAbility(new SacrificePermanentTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_PERMANENT
        ));

        // {1}{R}, {T}, Sacrifice a creature or enchantment: Blood Aspirant deals 1 damage to target creature. That creature can't block this turn.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(1), new ManaCostsImpl<>("{1}{R}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(filter));
        ability.addEffect(new CantBlockTargetEffect(Duration.EndOfTurn)
                .setText("That creature can't block this turn."));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private BloodAspirant(final BloodAspirant card) {
        super(card);
    }

    @Override
    public BloodAspirant copy() {
        return new BloodAspirant(this);
    }
}
