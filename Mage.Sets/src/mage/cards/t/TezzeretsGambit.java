
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author North
 */
public final class TezzeretsGambit extends CardImpl {

    public TezzeretsGambit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U/P}");

        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
        this.getSpellAbility().addEffect(new ProliferateEffect());
    }

    public TezzeretsGambit(final TezzeretsGambit card) {
        super(card);
    }

    @Override
    public TezzeretsGambit copy() {
        return new TezzeretsGambit(this);
    }
}
