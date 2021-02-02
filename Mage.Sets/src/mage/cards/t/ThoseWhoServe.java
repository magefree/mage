
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class ThoseWhoServe extends CardImpl {

    public ThoseWhoServe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);
    }

    private ThoseWhoServe(final ThoseWhoServe card) {
        super(card);
    }

    @Override
    public ThoseWhoServe copy() {
        return new ThoseWhoServe(this);
    }
}
