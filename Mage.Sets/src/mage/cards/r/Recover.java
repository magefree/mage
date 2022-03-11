package mage.cards.r;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author dustinconrad
 */
public final class Recover extends CardImpl {

    public Recover(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Return target creature card from your graveyard to your hand.
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).setText("<br>"));
    }

    private Recover(final Recover card) {
        super(card);
    }

    @Override
    public Recover copy() {
        return new Recover(this);
    }
}
