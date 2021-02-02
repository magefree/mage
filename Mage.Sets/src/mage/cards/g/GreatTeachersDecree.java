
package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author fireshoes
 */
public final class GreatTeachersDecree extends CardImpl {

    public GreatTeachersDecree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{W}");

        // Creatures you control get +2/+1 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(2, 1, Duration.EndOfTurn));
        
        // Rebound
        this.addAbility(new ReboundAbility());
    }

    private GreatTeachersDecree(final GreatTeachersDecree card) {
        super(card);
    }

    @Override
    public GreatTeachersDecree copy() {
        return new GreatTeachersDecree(this);
    }
}
