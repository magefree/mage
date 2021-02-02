
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class WallOfStone extends CardImpl {

    public WallOfStone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{R}");
        this.subtype.add(SubType.WALL);

        this.power = new MageInt(0);
        this.toughness = new MageInt(8);

        this.addAbility(DefenderAbility.getInstance());
    }

    private WallOfStone(final WallOfStone card) {
        super(card);
    }

    @Override
    public WallOfStone copy() {
        return new WallOfStone(this);
    }
}
