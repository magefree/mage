
package mage.cards.c;

import java.util.UUID;
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
public final class ControlOfTheCourt extends CardImpl {

    public ControlOfTheCourt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}");

        // Draw four cards, then discard three cards at random.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(4));
        Effect effect = new DiscardControllerEffect(3, true);
        effect.concatBy(", then");
        this.getSpellAbility().addEffect(effect);
    }

    private ControlOfTheCourt(final ControlOfTheCourt card) {
        super(card);
    }

    @Override
    public ControlOfTheCourt copy() {
        return new ControlOfTheCourt(this);
    }
}
