
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.DamageAllControlledTargetEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author fireshoes
 */
public final class FlameWave extends CardImpl {

    public FlameWave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}{R}{R}");

        // Flame Wave deals 4 damage to target player and each creature they control.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());
        this.getSpellAbility().addEffect(new DamageAllControlledTargetEffect(4, new FilterCreaturePermanent())
                .setText("and each creature that player or that planeswalker's controller controls")
        );
    }

    private FlameWave(final FlameWave card) {
        super(card);
    }

    @Override
    public FlameWave copy() {
        return new FlameWave(this);
    }
}
