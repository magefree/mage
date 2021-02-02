
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class MahamotiDjinn extends CardImpl {

    public MahamotiDjinn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{U}");
        this.subtype.add(SubType.DJINN);

        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        this.addAbility(FlyingAbility.getInstance());
    }

    private MahamotiDjinn(final MahamotiDjinn card) {
        super(card);
    }

    @Override
    public MahamotiDjinn copy() {
        return new MahamotiDjinn(this);
    }
}
