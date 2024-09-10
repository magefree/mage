package mage.cards.g;

import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class GrimTutor extends CardImpl {

    public GrimTutor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        // Search your library for a card and put that card into your hand, then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(), false, true));

        // You lose 3 life.
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(3));
    }

    private GrimTutor(final GrimTutor card) {
        super(card);
    }

    @Override
    public GrimTutor copy() {
        return new GrimTutor(this);
    }
}
