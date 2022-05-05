package mage.cards.n;

import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class NaturesSpiral extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard("permanent card from your graveyard");

    public NaturesSpiral(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
    }

    private NaturesSpiral(final NaturesSpiral card) {
        super(card);
    }

    @Override
    public NaturesSpiral copy() {
        return new NaturesSpiral(this);
    }
}
