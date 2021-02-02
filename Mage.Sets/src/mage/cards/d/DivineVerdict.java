
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterAttackingOrBlockingCreature;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class DivineVerdict extends CardImpl {

    public DivineVerdict(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");

        // Destroy target attacking or blocking creature.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(new FilterAttackingOrBlockingCreature()));
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
    }

    private DivineVerdict(final DivineVerdict card) {
        super(card);
    }

    @Override
    public DivineVerdict copy() {
        return new DivineVerdict(this);
    }
}
