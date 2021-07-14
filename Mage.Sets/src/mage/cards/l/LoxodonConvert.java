
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class LoxodonConvert extends CardImpl {

    public LoxodonConvert(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(4);
        this.toughness = new MageInt(2);
    }

    private LoxodonConvert(final LoxodonConvert card) {
        super(card);
    }

    @Override
    public LoxodonConvert copy() {
        return new LoxodonConvert(this);
    }
}
