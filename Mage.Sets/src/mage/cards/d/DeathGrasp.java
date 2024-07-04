

package mage.cards.d;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.GetXValue;
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


        this.getSpellAbility().addEffect(new DamageTargetEffect(GetXValue.instance));
        this.getSpellAbility().addEffect(new GainLifeEffect(GetXValue.instance));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private DeathGrasp(final DeathGrasp card) {
        super(card);
    }

    @Override
    public DeathGrasp copy() {
        return new DeathGrasp(this);
    }

}
