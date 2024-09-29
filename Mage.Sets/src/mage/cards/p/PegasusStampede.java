
package mage.cards.p;

import java.util.UUID;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.PegasusToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class PegasusStampede extends CardImpl {

    public PegasusStampede(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{W}");

        // Buyback-Sacrifice a land.
        this.addAbility(new BuybackAbility(new SacrificeTargetCost(StaticFilters.FILTER_LAND)));

        // Create a 1/1 white Pegasus creature token with flying.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new PegasusToken()));
    }

    private PegasusStampede(final PegasusStampede card) {
        super(card);
    }

    @Override
    public PegasusStampede copy() {
        return new PegasusStampede(this);
    }
}
