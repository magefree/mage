
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class SupplantForm extends CardImpl {

    public SupplantForm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{U}{U}");

        // Return target creature to its owner's hand. You create a token that's a copy of that creature.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        Effect effect = new CreateTokenCopyTargetEffect();
        effect.setText("You create a token that's a copy of that creature");
        this.getSpellAbility().addEffect(effect);
    }

    private SupplantForm(final SupplantForm card) {
        super(card);
    }

    @Override
    public SupplantForm copy() {
        return new SupplantForm(this);
    }
}
