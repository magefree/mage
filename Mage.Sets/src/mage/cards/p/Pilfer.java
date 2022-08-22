package mage.cards.p;

import java.util.UUID;

import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;

/**
 *
 * @author TheElk801
 */
public final class Pilfer extends CardImpl {

    public Pilfer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Target opponent reveals their hand. You choose a nonland card from it. That player discards that card.
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(
                StaticFilters.FILTER_CARD_NON_LAND, TargetController.ANY
        ));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private Pilfer(final Pilfer card) {
        super(card);
    }

    @Override
    public Pilfer copy() {
        return new Pilfer(this);
    }
}
