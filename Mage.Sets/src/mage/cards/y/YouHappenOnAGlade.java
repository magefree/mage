package mage.cards.y;

import mage.abilities.Mode;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPermanentCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author Raphael-Schulz
 */
public final class YouHappenOnAGlade extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard("permanent card from your graveyard");

    public YouHappenOnAGlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Choose one —
        // • Journey On — Search your library for up to two basic land cards, reveal them, put them into your hand, then shuffle.
        this.getSpellAbility().addEffect(
                new SearchLibraryPutInHandEffect(
                        new TargetCardInLibrary(0, 2, StaticFilters.FILTER_CARD_BASIC_LANDS), true)
        );
        this.getSpellAbility().withFirstModeFlavorWord("Journey On");

        // • Make Camp — Return target permanent card from your graveyard to your hand.
        Mode mode = new Mode((new ReturnFromGraveyardToHandTargetEffect()));
        mode.addTarget(new TargetCardInYourGraveyard(filter));
        this.getSpellAbility().addMode(mode.withFlavorWord("Make Camp"));
    }

    private YouHappenOnAGlade(final YouHappenOnAGlade card) {
        super(card);
    }

    @Override
    public YouHappenOnAGlade copy() {
        return new YouHappenOnAGlade(this);
    }
}
