package mage.cards.t;

import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardWithDifferentNameInLibrary;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ThreeDreams extends CardImpl {

    private static final FilterCard aurafilter = new FilterCard("Aura cards with different names");

    static {
        aurafilter.add(SubType.AURA.getPredicate());
    }

    public ThreeDreams(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}");

        // Search your library for up to three Aura cards with different names, reveal them, and put them into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(
                new TargetCardWithDifferentNameInLibrary(0, 3, aurafilter),
                true
        ));
    }

    private ThreeDreams(final ThreeDreams card) {
        super(card);
    }

    @Override
    public ThreeDreams copy() {
        return new ThreeDreams(this);
    }
}
