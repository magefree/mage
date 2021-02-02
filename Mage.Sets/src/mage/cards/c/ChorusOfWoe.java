
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author fireshoes
 */
public final class ChorusOfWoe extends CardImpl {

    public ChorusOfWoe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}");

        // Creatures you control get +1/+0 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(1, 0, Duration.EndOfTurn));
    }

    private ChorusOfWoe(final ChorusOfWoe card) {
        super(card);
    }

    @Override
    public ChorusOfWoe copy() {
        return new ChorusOfWoe(this);
    }
}
