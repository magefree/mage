
package mage.cards.v;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author North
 */
public final class VolcanicGeyser extends CardImpl {

    public VolcanicGeyser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{R}{R}");


        // Volcanic Geyser deals X damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(ManacostVariableValue.REGULAR));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private VolcanicGeyser(final VolcanicGeyser card) {
        super(card);
    }

    @Override
    public VolcanicGeyser copy() {
        return new VolcanicGeyser(this);
    }
}
