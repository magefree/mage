
package mage.cards.m;

import java.util.UUID;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author fireshoes
 */
public final class MagmaticInsight extends CardImpl {

    public MagmaticInsight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{R}");

        // As an additional cost to cast Magmatic Insight, discard a land card.
        this.getSpellAbility().addCost(new DiscardTargetCost(new TargetCardInHand(new FilterLandCard())));
        
        // Draw two cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
    }

    private MagmaticInsight(final MagmaticInsight card) {
        super(card);
    }

    @Override
    public MagmaticInsight copy() {
        return new MagmaticInsight(this);
    }
}
