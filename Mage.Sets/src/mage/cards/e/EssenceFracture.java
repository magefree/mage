
package mage.cards.e;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class EssenceFracture extends CardImpl {

    public EssenceFracture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}{U}");

        // Return two target creatures to their owners' hands.
        Effect effect = new ReturnToHandTargetEffect();
        effect.setText("Return two target creatures to their owners' hands");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(2));
        
        // Cycling {2}{U}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}{U}")));
    }

    private EssenceFracture(final EssenceFracture card) {
        super(card);
    }

    @Override
    public EssenceFracture copy() {
        return new EssenceFracture(this);
    }
}
