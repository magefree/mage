
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class PinnacleOfRage extends CardImpl {

    public PinnacleOfRage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}{R}");

        // Pinnacle of Rage deals 3 damage to each of two target creatures and/or players.
        Effect effect = new DamageTargetEffect(3);
        effect.setText("{this} deals 3 damage to each of two targets");
        this.getSpellAbility().addTarget(new TargetAnyTarget(2, 2));
        this.getSpellAbility().addEffect(effect);
    }

    private PinnacleOfRage(final PinnacleOfRage card) {
        super(card);
    }

    @Override
    public PinnacleOfRage copy() {
        return new PinnacleOfRage(this);
    }
}
