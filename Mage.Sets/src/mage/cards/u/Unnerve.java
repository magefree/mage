
package mage.cards.u;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

/**
 *
 * @author fireshoes
 */
public final class Unnerve extends CardImpl {

    public Unnerve(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}");

        // Each opponent discards two cards.
        this.getSpellAbility().addEffect(new DiscardEachPlayerEffect(StaticValue.get(2), false, TargetController.OPPONENT));
    }

    private Unnerve(final Unnerve card) {
        super(card);
    }

    @Override
    public Unnerve copy() {
        return new Unnerve(this);
    }
}
