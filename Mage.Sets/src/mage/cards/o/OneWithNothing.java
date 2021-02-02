

package mage.cards.o;

import java.util.UUID;

import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author LevelX2
 */
public final class OneWithNothing extends CardImpl {

    public OneWithNothing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Discard your hand.
        this.getSpellAbility().addEffect(new DiscardHandControllerEffect());
    }

    private OneWithNothing(final OneWithNothing card) {
        super(card);
    }

    @Override
    public OneWithNothing copy() {
        return new OneWithNothing(this);
    }
}
