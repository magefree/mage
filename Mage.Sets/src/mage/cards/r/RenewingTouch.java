package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.ShuffleIntoLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author TheElk801
 */
public final class RenewingTouch extends CardImpl {

    public RenewingTouch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Shuffle any number of target creature cards from your graveyard into your library.
        this.getSpellAbility().addEffect(new ShuffleIntoLibraryTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, Integer.MAX_VALUE, StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD));
    }

    private RenewingTouch(final RenewingTouch card) {
        super(card);
    }

    @Override
    public RenewingTouch copy() {
        return new RenewingTouch(this);
    }
}
