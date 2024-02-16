
package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author jonubuu
 */
public final class GoblinLore extends CardImpl {

    public GoblinLore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}");


        // Draw four cards, then discard three cards at random.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(4));
        Effect effect = new DiscardControllerEffect(3, true);
        effect.setText(", then discard three cards at random");
        this.getSpellAbility().addEffect(effect);    }

    private GoblinLore(final GoblinLore card) {
        super(card);
    }

    @Override
    public GoblinLore copy() {
        return new GoblinLore(this);
    }
}
