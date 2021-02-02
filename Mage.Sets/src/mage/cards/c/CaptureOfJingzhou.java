
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Plopman
 */
public final class CaptureOfJingzhou extends CardImpl {

    public CaptureOfJingzhou(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}{U}");


        // Take an extra turn after this one.
        this.getSpellAbility().addEffect(new AddExtraTurnControllerEffect());
    }

    private CaptureOfJingzhou(final CaptureOfJingzhou card) {
        super(card);
    }

    @Override
    public CaptureOfJingzhou copy() {
        return new CaptureOfJingzhou(this);
    }
}
