
package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.keyword.BolsterEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterAttackingOrBlockingCreature;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class EnduringVictory extends CardImpl {

    public EnduringVictory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{W}");

        // Destroy target attacking or blocking creature. Bolster 1.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(new FilterAttackingOrBlockingCreature()));
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new BolsterEffect(1));
    }

    private EnduringVictory(final EnduringVictory card) {
        super(card);
    }

    @Override
    public EnduringVictory copy() {
        return new EnduringVictory(this);
    }
}
