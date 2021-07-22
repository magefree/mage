
package mage.cards.b;

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
public final class Blaze extends CardImpl {

    public Blaze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{R}");


        // Blaze deals X damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(ManacostVariableValue.REGULAR));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private Blaze(final Blaze card) {
        super(card);
    }

    @Override
    public Blaze copy() {
        return new Blaze(this);
    }
}
