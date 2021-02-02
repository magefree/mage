
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.UntapLandsEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author fireshoes
 */
public final class PoreOverThePages extends CardImpl {

    public PoreOverThePages(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}{U}");

        // Draw three cards, untap up to two lands, then discard a card.
        Effect effect = new DrawCardSourceControllerEffect(3);
        effect.setText("Draw three cards");
        this.getSpellAbility().addEffect(effect);
        effect = new UntapLandsEffect(2);
        effect.setText(", untap up to two lands");
        this.getSpellAbility().addEffect(effect);
        effect = new DiscardControllerEffect(1);
        effect.setText(", then discard a card");
        this.getSpellAbility().addEffect(effect);
    }

    private PoreOverThePages(final PoreOverThePages card) {
        super(card);
    }

    @Override
    public PoreOverThePages copy() {
        return new PoreOverThePages(this);
    }
}
