
package mage.cards.w;

import java.util.UUID;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author North
 */
public final class WildGuess extends CardImpl {

    public WildGuess(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{R}{R}");


        // As an additional cost to cast Wild Guess, discard a card.
        this.getSpellAbility().addCost(new DiscardTargetCost(new TargetCardInHand()));
        // Draw two cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
    }

    public WildGuess(final WildGuess card) {
        super(card);
    }

    @Override
    public WildGuess copy() {
        return new WildGuess(this);
    }
}
