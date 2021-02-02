
package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class HoldAtBay extends CardImpl {

    public HoldAtBay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");


        // Prevent the next 7 damage that would be dealt to any target this turn.
        this.getSpellAbility().addEffect(new PreventDamageToTargetEffect(Duration.EndOfTurn, 7));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private HoldAtBay(final HoldAtBay card) {
        super(card);
    }

    @Override
    public HoldAtBay copy() {
        return new HoldAtBay(this);
    }
}
