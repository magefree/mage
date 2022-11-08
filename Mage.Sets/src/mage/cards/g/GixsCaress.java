package mage.cards.g;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.permanent.token.PowerstoneToken;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GixsCaress extends CardImpl {

    public GixsCaress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Target opponent reveals their hand. You choose a nonland card from it. That player discards that card.
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(
                StaticFilters.FILTER_CARD_NON_LAND, TargetController.OPPONENT
        ));
        this.getSpellAbility().addTarget(new TargetOpponent());

        // Create a tapped Powerstone token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new PowerstoneToken(), 1, true).concatBy("<br>"));
    }

    private GixsCaress(final GixsCaress card) {
        super(card);
    }

    @Override
    public GixsCaress copy() {
        return new GixsCaress(this);
    }
}
