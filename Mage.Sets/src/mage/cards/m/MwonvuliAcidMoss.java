
package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterLandCard;
import mage.filter.common.FilterLandPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author markedagain
 */
public final class MwonvuliAcidMoss extends CardImpl {

    private static final FilterLandCard filterForest = new FilterLandCard("Forest card");

    static {
        filterForest.add(SubType.FOREST.getPredicate());
    }

    public MwonvuliAcidMoss(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}{G}");

        // Destroy target land. Search your library for a Forest card and put that card onto the battlefield tapped. Then shuffle your library.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(new FilterLandPermanent()));
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filterForest), true, true));
    }

    private MwonvuliAcidMoss(final MwonvuliAcidMoss card) {
        super(card);
    }

    @Override
    public MwonvuliAcidMoss copy() {
        return new MwonvuliAcidMoss(this);
    }
}
