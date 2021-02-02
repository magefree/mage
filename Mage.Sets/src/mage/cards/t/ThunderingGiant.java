
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class ThunderingGiant extends CardImpl {

    public ThunderingGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        this.addAbility(HasteAbility.getInstance());
    }

    private ThunderingGiant(final ThunderingGiant card) {
        super(card);
    }

    @Override
    public ThunderingGiant copy() {
        return new ThunderingGiant(this);
    }
}
