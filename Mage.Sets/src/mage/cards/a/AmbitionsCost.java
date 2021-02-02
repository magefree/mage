
package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author North
 */
public final class AmbitionsCost extends CardImpl {

    public AmbitionsCost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}");


        // You draw three cards and you lose 3 life.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3).setText("you draw three cards"));
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(3).concatBy("and"));
    }

    private AmbitionsCost(final AmbitionsCost card) {
        super(card);
    }

    @Override
    public AmbitionsCost copy() {
        return new AmbitionsCost(this);
    }
}
