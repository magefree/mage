
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author Ekkaia
 */
public final class ScareTactics extends CardImpl {

    public ScareTactics(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");

        // Creatures you control get +1/+0 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(1, 0, Duration.EndOfTurn));
    }

    private ScareTactics(final ScareTactics card) {
        super(card);
    }

    @Override
    public ScareTactics copy() {
        return new ScareTactics(this);
    }
}