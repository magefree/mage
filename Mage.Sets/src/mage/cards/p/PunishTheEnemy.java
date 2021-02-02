
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author LevelX2
 */
public final class PunishTheEnemy extends CardImpl {

    public PunishTheEnemy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}");

        // Punish the Enemy deals 3 damage to target player and 3 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3, true, "target player or planeswalker and 3 damage to target creature"));
        Target target = new TargetPlayerOrPlaneswalker();
        this.getSpellAbility().addTarget(target);
        target = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(target);
    }

    private PunishTheEnemy(final PunishTheEnemy card) {
        super(card);
    }

    @Override
    public PunishTheEnemy copy() {
        return new PunishTheEnemy(this);
    }

}
