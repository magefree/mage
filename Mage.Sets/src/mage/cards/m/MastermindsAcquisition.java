package mage.cards.m;

import mage.abilities.Mode;
import mage.abilities.effects.common.WishEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.hint.common.OpenSideboardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author L_J
 */
public final class MastermindsAcquisition extends CardImpl {

    public MastermindsAcquisition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");

        // Choose one - 
        // Search your library for a card and put that card into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary()));

        // Put a card you own from outside the game into your hand.
        Mode mode = new Mode(new WishEffect().setText("put a card you own from outside the game into your hand"));
        this.getSpellAbility().addMode(mode);
        this.getSpellAbility().addHint(OpenSideboardHint.instance);
    }

    private MastermindsAcquisition(final MastermindsAcquisition card) {
        super(card);
    }

    @Override
    public MastermindsAcquisition copy() {
        return new MastermindsAcquisition(this);
    }
}
