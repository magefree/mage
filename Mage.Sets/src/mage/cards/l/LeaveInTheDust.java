
package mage.cards.l;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author Styxo
 */
public final class LeaveInTheDust extends CardImpl {

    public LeaveInTheDust(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Return target nonland permanent to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));

    }

    private LeaveInTheDust(final LeaveInTheDust card) {
        super(card);
    }

    @Override
    public LeaveInTheDust copy() {
        return new LeaveInTheDust(this);
    }
}
