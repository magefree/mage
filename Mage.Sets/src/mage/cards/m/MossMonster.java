
package mage.cards.m;

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
public final class MossMonster extends CardImpl {

    public MossMonster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(6);
    }

    private MossMonster(final MossMonster card) {
        super(card);
    }

    @Override
    public MossMonster copy() {
        return new MossMonster(this);
    }
}
