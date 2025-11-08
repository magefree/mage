package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.OneOrMoreCombatDamagePlayerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeroesInAHalfShell extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("Mutants, Ninjas, and/or Turtles you control");

    static {
        filter.add(Predicates.or(
                SubType.MUTANT.getPredicate(),
                SubType.NINJA.getPredicate(),
                SubType.TURTLE.getPredicate()
        ));
    }

    public HeroesInAHalfShell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}{B}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever one or more Mutants, Ninjas, and/or Turtles you control deal combat damage to a player, put a +1/+1 counter on each of those creatures and draw a card.
        Ability ability = new OneOrMoreCombatDamagePlayerTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                        .setText("put a +1/+1 counter on each of those creatures"),
                SetTargetPointer.PERMANENT, filter, false
        );
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private HeroesInAHalfShell(final HeroesInAHalfShell card) {
        super(card);
    }

    @Override
    public HeroesInAHalfShell copy() {
        return new HeroesInAHalfShell(this);
    }
}
