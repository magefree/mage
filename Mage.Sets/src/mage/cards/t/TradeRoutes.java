
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterLandCard;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author dustinconrad
 */
public final class TradeRoutes extends CardImpl {

    public TradeRoutes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}");


        // {1}: Return target land you control to its owner's hand.
        Effect returnToHand = new ReturnToHandTargetEffect();
        Target targetLandYouControl = new TargetPermanent(new FilterControlledLandPermanent());
        Ability returnLandToHand = new SimpleActivatedAbility(Zone.BATTLEFIELD, returnToHand, new GenericManaCost(1));
        returnLandToHand.addTarget(targetLandYouControl);
        this.addAbility(returnLandToHand);

        // {1}, Discard a land card: Draw a card.
        Ability drawACard = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new GenericManaCost(1));
        drawACard.addCost(new DiscardTargetCost(new TargetCardInHand(new FilterLandCard("a land card"))));
        this.addAbility(drawACard);
    }

    private TradeRoutes(final TradeRoutes card) {
        super(card);
    }

    @Override
    public TradeRoutes copy() {
        return new TradeRoutes(this);
    }
}
