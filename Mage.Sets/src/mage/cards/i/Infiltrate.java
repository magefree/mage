
package mage.cards.i;

import java.util.UUID;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author AlumiuN
 */
public final class Infiltrate extends CardImpl {

    public Infiltrate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        // Target creature is unblockable this turn.
        this.getSpellAbility().addEffect(new CantBeBlockedTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Infiltrate(final Infiltrate card) {
        super(card);
    }

    @Override
    public Infiltrate copy() {
        return new Infiltrate(this);
    }
}
