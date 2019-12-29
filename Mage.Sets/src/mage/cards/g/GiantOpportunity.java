package mage.cards.g;

import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.FoodToken;
import mage.game.permanent.token.GiantOpportunityToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GiantOpportunity extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.FOOD, "Foods");

    public GiantOpportunity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // You may sacrifice two Foods. If you do, create a 7/7 green Giant creature token. Otherwise, create three Food tokens.
        this.getSpellAbility().addEffect(new DoIfCostPaid(
                new CreateTokenEffect(new GiantOpportunityToken()),
                new CreateTokenEffect(new FoodToken(), 3),
                new SacrificeTargetCost(new TargetControlledPermanent(2, filter))
        ).setText("You may sacrifice two Foods. If you do, create a 7/7 green Giant creature token. " +
                "Otherwise, create three Food tokens."));
    }

    private GiantOpportunity(final GiantOpportunity card) {
        super(card);
    }

    @Override
    public GiantOpportunity copy() {
        return new GiantOpportunity(this);
    }
}
