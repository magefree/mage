package mage.cards.d;

import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class DisturbedBurial extends CardImpl {

    public DisturbedBurial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Buyback {3}
        this.addAbility(new BuybackAbility("{3}"));
        // Return target creature card from your graveyard to your hand.
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
    }

    private DisturbedBurial(final DisturbedBurial card) {
        super(card);
    }

    @Override
    public DisturbedBurial copy() {
        return new DisturbedBurial(this);
    }
}
