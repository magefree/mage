
package mage.cards.a;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;

/**
 *
 * @author fireshoes
 */
public final class Anticipate extends CardImpl {

    public Anticipate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");

        // Look at the top three cards of your library. Put one of them into your hand and the rest on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect
            (new StaticValue(3), false, new StaticValue(1), new FilterCard(), Zone.LIBRARY, false, false));
    }

    public Anticipate(final Anticipate card) {
        super(card);
    }

    @Override
    public Anticipate copy() {
        return new Anticipate(this);
    }
}
