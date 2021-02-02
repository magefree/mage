
package mage.cards.s;

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
 * @author North
 */
public final class SleightOfHand extends CardImpl {

    public SleightOfHand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{U}");


        // Look at the top two cards of your library. Put one of them into your hand and the other on the bottom of your library.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(StaticValue.get(2), false, StaticValue.get(1), new FilterCard(), Zone.LIBRARY, false, false));
    }

    private SleightOfHand(final SleightOfHand card) {
        super(card);
    }

    @Override
    public SleightOfHand copy() {
        return new SleightOfHand(this);
    }
}
