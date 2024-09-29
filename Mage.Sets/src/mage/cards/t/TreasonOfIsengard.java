package mage.cards.t;

import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TreasonOfIsengard extends CardImpl {

    public TreasonOfIsengard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Put up to one target instant or sorcery card from your graveyard on top of your library.
        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(
                0, 1, StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD
        ));

        // Amass Orcs 2.
        this.getSpellAbility().addEffect(new AmassEffect(2, SubType.ORC).concatBy("<br>"));
    }

    private TreasonOfIsengard(final TreasonOfIsengard card) {
        super(card);
    }

    @Override
    public TreasonOfIsengard copy() {
        return new TreasonOfIsengard(this);
    }
}
