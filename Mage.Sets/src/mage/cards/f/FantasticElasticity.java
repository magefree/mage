package mage.cards.f;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author muz
 */
public final class FantasticElasticity extends CardImpl {

    public FantasticElasticity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");


        // Choose one --
        // * Return target nonland permanent to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());

        // * Return target instant or sorcery card from your graveyard to your hand.
        this.getSpellAbility().addMode(new Mode(new ReturnFromGraveyardToHandTargetEffect())
            .addTarget(new TargetCardInYourGraveyard(
                StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD
            ))
        );

        // Rebound
        this.addAbility(new ReboundAbility());
    }

    private FantasticElasticity(final FantasticElasticity card) {
        super(card);
    }

    @Override
    public FantasticElasticity copy() {
        return new FantasticElasticity(this);
    }
}
