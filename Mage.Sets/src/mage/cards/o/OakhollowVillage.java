package mage.cards.o;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.EnteredThisTurnPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OakhollowVillage extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(
            "Frog, Rabbit, Raccoon, or Squirrel you control that entered the battlefield this turn"
    );

    static {
        filter.add(Predicates.or(
                SubType.FROG.getPredicate(),
                SubType.RABBIT.getPredicate(),
                SubType.RACCOON.getPredicate(),
                SubType.SQUIRREL.getPredicate()
        ));
        filter.add(EnteredThisTurnPredicate.instance);
    }

    public OakhollowVillage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Add {G}. Spend this mana only to cast a creature spell.
        this.addAbility(new ConditionalColoredManaAbility(
                Mana.GreenMana(1), new ConditionalSpellManaBuilder(StaticFilters.FILTER_SPELL_A_CREATURE)
        ));

        // {G}, {T}: Put a +1/+1 counter on each Frog, Rabbit, Raccoon, or Squirrel you control that entered the battlefield this turn.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter), new ManaCostsImpl<>("{G}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private OakhollowVillage(final OakhollowVillage card) {
        super(card);
    }

    @Override
    public OakhollowVillage copy() {
        return new OakhollowVillage(this);
    }
}
