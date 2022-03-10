
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Loki
 */
public final class InvokeTheFiremind extends CardImpl {

    public InvokeTheFiremind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{U}{U}{R}");


        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(ManacostVariableValue.REGULAR));
        Mode mode = new Mode(new DamageTargetEffect(ManacostVariableValue.REGULAR));
        mode.addTarget(new TargetAnyTarget());
        this.getSpellAbility().addMode(mode);
    }

    private InvokeTheFiremind(final InvokeTheFiremind card) {
        super(card);
    }

    @Override
    public InvokeTheFiremind copy() {
        return new InvokeTheFiremind(this);
    }
}
