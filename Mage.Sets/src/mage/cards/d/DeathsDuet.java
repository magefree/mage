package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author fireshoes
 */
public final class DeathsDuet extends CardImpl {

    public DeathsDuet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");

        // Return two target creature cards from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(2, StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD));
    }

    private DeathsDuet(final DeathsDuet card) {
        super(card);
    }

    @Override
    public DeathsDuet copy() {
        return new DeathsDuet(this);
    }
}
