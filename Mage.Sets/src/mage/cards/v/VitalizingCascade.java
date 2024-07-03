
package mage.cards.v;

import mage.abilities.dynamicvalue.IntPlusDynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class VitalizingCascade extends CardImpl {

    public VitalizingCascade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{G}{W}");

        // You gain X plus 3 life.
        this.getSpellAbility().addEffect(new GainLifeEffect(new IntPlusDynamicValue(3, ManacostVariableValue.instance)));
    }

    private VitalizingCascade(final VitalizingCascade card) {
        super(card);
    }

    @Override
    public VitalizingCascade copy() {
        return new VitalizingCascade(this);
    }
}
