package mage.cards.g;

import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.NamePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GideonsBattleCry extends CardImpl {

    private static final FilterCard filter = new FilterCard("Gideon, the Oathsworn");

    static {
        filter.add(new NamePredicate("Gideon, the Oathsworn"));
    }

    public GideonsBattleCry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}{W}");

        // Put a +1/+1 counter on each creature you control. You may search your library and/or graveyard for a card named Gideon, the Oathsworn, reveal it, and put it into your hand. If you search your library this way, shuffle it.
        this.getSpellAbility().addEffect(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE
        ));
        this.getSpellAbility().addEffect(new SearchLibraryGraveyardPutInHandEffect(
                filter, false, true
        ));
    }

    private GideonsBattleCry(final GideonsBattleCry card) {
        super(card);
    }

    @Override
    public GideonsBattleCry copy() {
        return new GideonsBattleCry(this);
    }
}
