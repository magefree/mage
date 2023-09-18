package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author fireshoes
 */
public final class Reinforcements extends CardImpl {

    public Reinforcements(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");

        // Put up to three target creature cards from your graveyard on top of your library.
        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 3, StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD));
    }

    private Reinforcements(final Reinforcements card) {
        super(card);
    }

    @Override
    public Reinforcements copy() {
        return new Reinforcements(this);
    }
}
