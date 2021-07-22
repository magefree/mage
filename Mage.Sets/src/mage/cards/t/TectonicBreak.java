
package mage.cards.t;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledLandPermanent;

/**
 *
 * @author LoneFox
 */
public final class TectonicBreak extends CardImpl {

    public TectonicBreak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{R}{R}");

        // Each player sacrifices X lands.
        this.getSpellAbility().addEffect(new SacrificeAllEffect(ManacostVariableValue.REGULAR, new FilterControlledLandPermanent("lands")));
    }

    private TectonicBreak(final TectonicBreak card) {
        super(card);
    }

    @Override
    public TectonicBreak copy() {
        return new TectonicBreak(this);
    }
}
