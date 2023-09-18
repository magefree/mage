
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.EntwineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author Plopman
 */
public final class ReapAndSow extends CardImpl {

    public ReapAndSow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}");


        // Choose one - 
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(1);
        //Destroy target land; 
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetLandPermanent());
        //or search your library for a land card, put that card onto the battlefield, then shuffle your library.
        Mode mode = new Mode(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(new FilterLandCard()), false, true));
        this.getSpellAbility().getModes().addMode(mode);

        // Entwine {1}{G}
        this.addAbility(new EntwineAbility("{1}{G}"));
    }

    private ReapAndSow(final ReapAndSow card) {
        super(card);
    }

    @Override
    public ReapAndSow copy() {
        return new ReapAndSow(this);
    }
}
