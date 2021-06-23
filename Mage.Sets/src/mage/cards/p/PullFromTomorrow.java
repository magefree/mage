
package mage.cards.p;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author fireshoes
 */
public final class PullFromTomorrow extends CardImpl {

    public PullFromTomorrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}{U}");

        // Draw X cards, then discard a card.
        getSpellAbility().addEffect(new DrawCardSourceControllerEffect(ManacostVariableValue.REGULAR));
        Effect effect = new DiscardControllerEffect(1);
        effect.setText(", then discard a card");
        getSpellAbility().addEffect(effect);
    }

    private PullFromTomorrow(final PullFromTomorrow card) {
        super(card);
    }

    @Override
    public PullFromTomorrow copy() {
        return new PullFromTomorrow(this);
    }
}
