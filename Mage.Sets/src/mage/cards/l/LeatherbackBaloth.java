

package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class LeatherbackBaloth extends CardImpl {

    public LeatherbackBaloth (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{G}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);
    }

    private LeatherbackBaloth(final LeatherbackBaloth card) {
        super(card);
    }

    @Override
    public LeatherbackBaloth copy() {
        return new LeatherbackBaloth(this);
    }

}
