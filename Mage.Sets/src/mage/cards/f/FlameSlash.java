
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class FlameSlash extends CardImpl {

    public FlameSlash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{R}");

        // Flame Slash deals 4 damage to target creature.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
    }

    private FlameSlash(final FlameSlash card) {
        super(card);
    }

    @Override
    public FlameSlash copy() {
        return new FlameSlash(this);
    }

}
