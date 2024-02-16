
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LookAtTargetPlayerHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author Loki
 */
public final class Peek extends CardImpl {

    public Peek(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        // Look at target player's hand.
        this.getSpellAbility().addEffect(new LookAtTargetPlayerHandEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private Peek(final Peek card) {
        super(card);
    }

    @Override
    public Peek copy() {
        return new Peek(this);
    }
}
