
package mage.cards.d;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.TargetManaValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author ayratn
 */
public final class DivineOffering extends CardImpl {

    public DivineOffering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");


        // Destroy target artifact. You gain life equal to its converted mana cost.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        Effect effect = new GainLifeEffect(TargetManaValue.instance);
        effect.setText("You gain life equal to its mana value");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
    }

    private DivineOffering(final DivineOffering card) {
        super(card);
    }

    @Override
    public DivineOffering copy() {
        return new DivineOffering(this);
    }
}
