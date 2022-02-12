package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 * @author TheElk801
 */
public final class ForeverYoung extends CardImpl {

    public ForeverYoung(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Put any number of target creature cards from your graveyard on top of your library.
        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, Integer.MAX_VALUE, StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    private ForeverYoung(final ForeverYoung card) {
        super(card);
    }

    @Override
    public ForeverYoung copy() {
        return new ForeverYoung(this);
    }
}
