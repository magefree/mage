
package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Loki
 */
public final class Withstand extends CardImpl {

    public Withstand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");

        this.getSpellAbility().addEffect(new PreventDamageToTargetEffect(Duration.EndOfTurn, 3));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
        // Prevent the next 3 damage that would be dealt to any target this turn.
        // Draw a card.
    }

    private Withstand(final Withstand card) {
        super(card);
    }

    @Override
    public Withstand copy() {
        return new Withstand(this);
    }
}
