
package mage.cards.b;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author KholdFuzion

 */
public final class Braingeyser extends CardImpl {

    public Braingeyser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{U}{U}");


        // Target player draws X cards.
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(ManacostVariableValue.REGULAR));
        this.getSpellAbility().addTarget(new TargetPlayer());
        
    }

    private Braingeyser(final Braingeyser card) {
        super(card);
    }

    @Override
    public Braingeyser copy() {
        return new Braingeyser(this);
    }
}
