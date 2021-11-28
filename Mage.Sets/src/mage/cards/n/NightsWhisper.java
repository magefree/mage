
package mage.cards.n;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author jonubuu
 */
public final class NightsWhisper extends CardImpl {

    public NightsWhisper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}");


        // You draw two cards and you lose 2 life.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2).setText("you draw two cards"));
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(2).concatBy("and"));
    }

    private NightsWhisper(final NightsWhisper card) {
        super(card);
    }

    @Override
    public NightsWhisper copy() {
        return new NightsWhisper(this);
    }
}
