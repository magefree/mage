package mage.cards.d;

import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardWithDifferentNameInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeathbellowWarCry extends CardImpl {

    private static final FilterCard minotaurFilter
            = new FilterCreatureCard("Minotaur creature cards with different names");

    static {
        minotaurFilter.add(SubType.MINOTAUR.getPredicate());
    }

    public DeathbellowWarCry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{R}{R}{R}");

        // Search your library for up to four Minotaur creature cards with different names, put them onto the battlefield, then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(
                new TargetCardWithDifferentNameInLibrary(0, 4, minotaurFilter)
        ));
    }

    private DeathbellowWarCry(final DeathbellowWarCry card) {
        super(card);
    }

    @Override
    public DeathbellowWarCry copy() {
        return new DeathbellowWarCry(this);
    }
}
