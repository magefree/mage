
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class WallOfAir extends CardImpl {

    public WallOfAir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{U}");
        this.subtype.add(SubType.WALL);

        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        this.addAbility(DefenderAbility.getInstance());
        this.addAbility(FlyingAbility.getInstance());
    }

    private WallOfAir(final WallOfAir card) {
        super(card);
    }

    @Override
    public WallOfAir copy() {
        return new WallOfAir(this);
    }
}
