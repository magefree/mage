package mage.cards.w;

import java.util.UUID;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author anonymous
 */
public final class WanderInDeath extends CardImpl {

    public WanderInDeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Return up to two target creature cards from your graveyard to your hand.
        getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 2, StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD));
        getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());

        // Cycling {2}
        this.addAbility(new CyclingAbility(new GenericManaCost(2)));
    }

    private WanderInDeath(final WanderInDeath card) {
        super(card);
    }

    @Override
    public WanderInDeath copy() {
        return new WanderInDeath(this);
    }
}
