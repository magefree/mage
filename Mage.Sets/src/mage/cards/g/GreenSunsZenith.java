
package mage.cards.g;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.ShuffleSpellEffect;
import mage.abilities.effects.common.search.SearchLibraryWithLessCMCPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 * @author Loki
 */
public final class GreenSunsZenith extends CardImpl {

    private static final FilterCard filter = new FilterCard("green creature card");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
        filter.add(CardType.CREATURE.getPredicate());
    }

    public GreenSunsZenith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G}");

        // Search your library for a green creature card with converted mana cost X or less, put it onto the battlefield, then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryWithLessCMCPutInPlayEffect(filter));

        // Shuffle Green Sun's Zenith into its owner's library.
        this.getSpellAbility().addEffect(ShuffleSpellEffect.getInstance());
    }

    private GreenSunsZenith(final GreenSunsZenith card) {
        super(card);
    }

    @Override
    public GreenSunsZenith copy() {
        return new GreenSunsZenith(this);
    }

}
