package mage.cards.a;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.BearToken;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AyulasInfluence extends CardImpl {

    public AyulasInfluence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}{G}{G}");

        // Discard a land card: Create a 2/2 green Bear creature token.
        this.addAbility(new SimpleActivatedAbility(
                new CreateTokenEffect(new BearToken()),
                new DiscardTargetCost(new TargetCardInHand(StaticFilters.FILTER_CARD_LAND_A))
        ));
    }

    private AyulasInfluence(final AyulasInfluence card) {
        super(card);
    }

    @Override
    public AyulasInfluence copy() {
        return new AyulasInfluence(this);
    }
}
