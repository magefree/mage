
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class BatheInDragonfire extends CardImpl {

    public BatheInDragonfire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}");

        // Bathe in Dragonfire deals 4 damage to target creature.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
    }

    private BatheInDragonfire(final BatheInDragonfire card) {
        super(card);
    }

    @Override
    public BatheInDragonfire copy() {
        return new BatheInDragonfire(this);
    }
}
