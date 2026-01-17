package mage.cards.s;

import java.util.UUID;

import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.SneakAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author muz
 */
public final class SplintersTechnique extends CardImpl {

    public SplintersTechnique(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Sneak {1}{B}
        this.addAbility(new SneakAbility(this, "{1}{B}"));

        // Search your library for a card, put that card into your hand, then shuffle.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(), false, true));
    }

    private SplintersTechnique(final SplintersTechnique card) {
        super(card);
    }

    @Override
    public SplintersTechnique copy() {
        return new SplintersTechnique(this);
    }
}
