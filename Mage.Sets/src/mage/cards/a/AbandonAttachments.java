package mage.cards.a;

import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AbandonAttachments extends CardImpl {

    public AbandonAttachments(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U/R}");

        this.subtype.add(SubType.LESSON);

        // You may discard a card. If you do, draw two cards.
        this.getSpellAbility().addEffect(new DoIfCostPaid(new DrawCardSourceControllerEffect(2), new DiscardCardCost()));
    }

    private AbandonAttachments(final AbandonAttachments card) {
        super(card);
    }

    @Override
    public AbandonAttachments copy() {
        return new AbandonAttachments(this);
    }
}
