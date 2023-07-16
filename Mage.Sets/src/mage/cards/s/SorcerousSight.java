
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LookAtTargetPlayerHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public final class SorcerousSight extends CardImpl {

    public SorcerousSight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{U}");

        // Look at target opponent's hand.
        Effect effect = new LookAtTargetPlayerHandEffect();
        effect.setText("Look at target opponent's hand");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetOpponent());
        
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private SorcerousSight(final SorcerousSight card) {
        super(card);
    }

    @Override
    public SorcerousSight copy() {
        return new SorcerousSight(this);
    }
}
