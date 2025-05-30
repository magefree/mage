package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.SpellsCostIncreasingAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterBySubtypeCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CharitableLevy extends CardImpl {

    private static final FilterCard filter = new FilterCard("noncreature spells");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    private static final Condition condition = new SourceHasCounterCondition(CounterType.COLLECTION, 3, Integer.MAX_VALUE);

    private static final FilterCard filterPlains = new FilterBySubtypeCard(SubType.PLAINS);

    public CharitableLevy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // Noncreature spells cost {1} more to cast.
        this.addAbility(new SimpleStaticAbility(
                new SpellsCostIncreasingAllEffect(1, filter, TargetController.ANY)
        ));

        // Whenever a player casts a noncreature spell, put a collection counter on Charitable Levy. Then if there are three or more collection counters on it, sacrifice it. If you do, draw a card, then you may search your library for a Plains card, put it onto the battlefield tapped, then shuffle.
        Ability ability = new SpellCastAllTriggeredAbility(
                new AddCountersSourceEffect(CounterType.COLLECTION.createInstance()),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE,
                false
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new DoIfCostPaid(
                        new DrawCardSourceControllerEffect(1),
                        null, new SacrificeSourceCost(), false
                ).addEffect(new SearchLibraryPutInPlayEffect(
                        new TargetCardInLibrary(filterPlains),
                        true, false, true
                )),
                condition,
                "Then if there are three or more collection counters on it, sacrifice it. "
                        + "If you do, draw a card, then you may search your library for a Plains card, "
                        + "put it onto the battlefield tapped, then shuffle"
        ));
        this.addAbility(ability);
    }

    private CharitableLevy(final CharitableLevy card) {
        super(card);
    }

    @Override
    public CharitableLevy copy() {
        return new CharitableLevy(this);
    }
}
