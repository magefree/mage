package mage.cards.v;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VastwoodSurge extends CardImpl {

    public VastwoodSurge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Kicker {4}
        this.addAbility(new KickerAbility("{4}"));

        // Search your library for up to two basic land cards, put them onto the battlefield tapped, then shuffle your library. If this spell was kicked, put two +1/+1 counters on each creature you control.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(0, 2, StaticFilters.FILTER_CARD_BASIC_LANDS), true
        ));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new AddCountersAllEffect(
                        CounterType.P1P1.createInstance(2),
                        StaticFilters.FILTER_CONTROLLED_CREATURES
                ), KickedCondition.ONCE, "If this spell was kicked, " +
                "put two +1/+1 counters on each creature you control."
        ));
    }

    private VastwoodSurge(final VastwoodSurge card) {
        super(card);
    }

    @Override
    public VastwoodSurge copy() {
        return new VastwoodSurge(this);
    }
}
