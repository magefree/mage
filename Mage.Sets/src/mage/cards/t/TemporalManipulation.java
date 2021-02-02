
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Plopman
 */
public final class TemporalManipulation extends CardImpl {

    public TemporalManipulation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}{U}");

        // Take an extra turn after this one.
        this.getSpellAbility().addEffect(new AddExtraTurnControllerEffect());
    }

    private TemporalManipulation(final TemporalManipulation card) {
        super(card);
    }

    @Override
    public TemporalManipulation copy() {
        return new TemporalManipulation(this);
    }
}
