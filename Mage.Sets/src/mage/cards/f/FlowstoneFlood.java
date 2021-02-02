
package mage.cards.f;

import java.util.UUID;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LevelX2
 */
public final class FlowstoneFlood extends CardImpl {

    public FlowstoneFlood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}");


        // Buyback—Pay 3 life, Discard a card at random. (You may pay 3 life and discard a card at random in addition to any other costs as you cast this spell. If you do, put this card into your hand as it resolves.)
        BuybackAbility buybackAbility = new BuybackAbility(new PayLifeCost(3));
        buybackAbility.addCost(new DiscardCardCost(true));
        this.addAbility(buybackAbility);

        // Destroy target land.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetLandPermanent());
    }

    private FlowstoneFlood(final FlowstoneFlood card) {
        super(card);
    }

    @Override
    public FlowstoneFlood copy() {
        return new FlowstoneFlood(this);
    }
}
