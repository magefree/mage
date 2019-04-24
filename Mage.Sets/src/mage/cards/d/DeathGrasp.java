

package mage.cards.d;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Loki
 */
public final class DeathGrasp extends CardImpl {

    public DeathGrasp (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{W}{B}");


        this.getSpellAbility().addEffect(new DamageTargetEffect(new ManacostVariableValue()));
        this.getSpellAbility().addEffect(new GainLifeEffect(new ManacostVariableValue()));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    public DeathGrasp (final DeathGrasp card) {
        super(card);
    }

    @Override
    public DeathGrasp copy() {
        return new DeathGrasp(this);
    }

}
