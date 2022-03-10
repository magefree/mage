package mage.cards.g;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.RecoverAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class GrimHarvest extends CardImpl {

    public GrimHarvest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Return target creature card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));

        // Recover {2}{B}
        this.addAbility(new RecoverAbility(new ManaCostsImpl<>("{2}{B}"), this));
    }

    private GrimHarvest(final GrimHarvest card) {
        super(card);
    }

    @Override
    public GrimHarvest copy() {
        return new GrimHarvest(this);
    }
}
