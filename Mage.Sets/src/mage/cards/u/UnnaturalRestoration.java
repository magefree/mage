package mage.cards.u;

import java.util.UUID;

import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 * @author TheElk801
 */
public final class UnnaturalRestoration extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard("permanent card from your graveyard");

    public UnnaturalRestoration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Return target permanent card from your graveyard to your hand. Proliferate.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));
        this.getSpellAbility().addEffect(new ProliferateEffect());
    }

    private UnnaturalRestoration(final UnnaturalRestoration card) {
        super(card);
    }

    @Override
    public UnnaturalRestoration copy() {
        return new UnnaturalRestoration(this);
    }
}
