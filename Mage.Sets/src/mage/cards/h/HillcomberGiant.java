
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.MountainwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class HillcomberGiant extends CardImpl {

    public HillcomberGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{W}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(new MountainwalkAbility());
    }

    private HillcomberGiant(final HillcomberGiant card) {
        super(card);
    }

    @Override
    public HillcomberGiant copy() {
        return new HillcomberGiant(this);
    }
}
