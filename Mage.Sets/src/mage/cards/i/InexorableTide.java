

package mage.cards.i;

import java.util.UUID;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Loki, North
 */
public final class InexorableTide extends CardImpl {

    public InexorableTide (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{U}{U}");


        this.addAbility(new SpellCastControllerTriggeredAbility(new ProliferateEffect(), false));
    }

    public InexorableTide (final InexorableTide card) {
        super(card);
    }

    @Override
    public InexorableTide copy() {
        return new InexorableTide(this);
    }

}
