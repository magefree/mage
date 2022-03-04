
package mage.cards.m;

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
public final class MarkovsServant extends CardImpl {

    public MarkovsServant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},null);
        this.subtype.add(SubType.VAMPIRE);
        this.color.setBlack(true);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.nightCard = true;
    }

    private MarkovsServant(final MarkovsServant card) {
        super(card);
    }

    @Override
    public MarkovsServant copy() {
        return new MarkovsServant(this);
    }
}
