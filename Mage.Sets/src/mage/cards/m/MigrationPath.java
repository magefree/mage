package mage.cards.m;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MigrationPath extends CardImpl {

    public MigrationPath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Search your library for up to two basic land cards, put them onto the battlefield tapped, then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(0, 2, StaticFilters.FILTER_CARD_BASIC_LANDS), true
        ));

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private MigrationPath(final MigrationPath card) {
        super(card);
    }

    @Override
    public MigrationPath copy() {
        return new MigrationPath(this);
    }
}
