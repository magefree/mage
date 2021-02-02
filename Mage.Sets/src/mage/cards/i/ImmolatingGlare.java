
package mage.cards.i;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterAttackingCreature;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class ImmolatingGlare extends CardImpl {

    public ImmolatingGlare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // Destroy target attacking creature.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(new FilterAttackingCreature()));
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
    }

    private ImmolatingGlare(final ImmolatingGlare card) {
        super(card);
    }

    @Override
    public ImmolatingGlare copy() {
        return new ImmolatingGlare(this);
    }
}
