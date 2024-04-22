package mage.cards.g;

import mage.abilities.Mode;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterBasicLandCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GlimpseTheCore extends CardImpl {

    private static final FilterCard filter = new FilterBasicLandCard(SubType.FOREST);
    private static final FilterCard filter2 = new FilterCard("Cave card from your graveyard");

    static {
        filter2.add(SubType.CAVE.getPredicate());
    }

    public GlimpseTheCore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Choose one --
        // * Search your library for a basic Forest card, put that card onto the battlefield tapped, then shuffle.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), true, true));

        // * Return target Cave card from your graveyard to the battlefield tapped.
        this.getSpellAbility().addMode(new Mode(new ReturnFromGraveyardToBattlefieldTargetEffect(true)).addTarget(new TargetCardInYourGraveyard(filter2)));
    }

    private GlimpseTheCore(final GlimpseTheCore card) {
        super(card);
    }

    @Override
    public GlimpseTheCore copy() {
        return new GlimpseTheCore(this);
    }
}
