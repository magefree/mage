
package mage.cards.d;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.DelveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;

/**
 *
 * @author emerald000
 */
public final class DigThroughTime extends CardImpl {

    public DigThroughTime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{6}{U}{U}");


        // Delve
        this.addAbility(new DelveAbility());
        
        // Look at the top seven cards of your library. Put two of them into your hand and the rest on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(new StaticValue(7), false, new StaticValue(2), new FilterCard(), Zone.LIBRARY, false, false));
    }

    public DigThroughTime(final DigThroughTime card) {
        super(card);
    }

    @Override
    public DigThroughTime copy() {
        return new DigThroughTime(this);
    }
}
