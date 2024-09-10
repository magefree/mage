package mage.cards.d;

import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author KholdFuzion
 */
public final class DemonicTutor extends CardImpl {

    public DemonicTutor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Search your library for a card and put that card into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(), false, true));
    }

    private DemonicTutor(final DemonicTutor card) {
        super(card);
    }

    @Override
    public DemonicTutor copy() {
        return new DemonicTutor(this);
    }
}
