package mage.cards.u;

import mage.abilities.effects.OneShotNonTargetEffect;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnsealTheNecropolis extends CardImpl {

    public UnsealTheNecropolis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Each player mills three cards. Then you return up to two creature cards from your graveyard to your hand.
        this.getSpellAbility().addEffect(new MillCardsEachPlayerEffect(3, TargetController.EACH_PLAYER));
        this.getSpellAbility().addEffect(new OneShotNonTargetEffect(new ReturnFromGraveyardToHandTargetEffect().setText("Then you return up to two creature cards from your graveyard to your hand"),
                new TargetCardInYourGraveyard(0, 2, StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD, true)));
    }

    private UnsealTheNecropolis(final UnsealTheNecropolis card) {
        super(card);
    }

    @Override
    public UnsealTheNecropolis copy() {
        return new UnsealTheNecropolis(this);
    }
}
