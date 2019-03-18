
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.WishEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author L_J
 */
public final class MastermindsAcquisition extends CardImpl {

    public MastermindsAcquisition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}{B}");

        // Choose one - 
        // Search your library for a card and put that card into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary()));
        
        // Choose a card you own from outside the game and put it into your hand.
        Mode mode = new Mode();
        mode.addEffect(new WishEffect(new FilterCard(), false));
        this.getSpellAbility().addMode(mode);
    }

    public MastermindsAcquisition(final MastermindsAcquisition card) {
        super(card);
    }

    @Override
    public MastermindsAcquisition copy() {
        return new MastermindsAcquisition(this);
    }
}
