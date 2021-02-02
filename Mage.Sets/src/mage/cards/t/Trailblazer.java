
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class Trailblazer extends CardImpl {

    public Trailblazer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}{G}");

        // Target creature is unblockable this turn.
        this.getSpellAbility().addEffect(new CantBeBlockedTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Trailblazer(final Trailblazer card) {
        super(card);
    }

    @Override
    public Trailblazer copy() {
        return new Trailblazer(this);
    }
}
