
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
public final class WallOfSwords extends CardImpl {

    public WallOfSwords(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.WALL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        this.addAbility(DefenderAbility.getInstance());
        this.addAbility(FlyingAbility.getInstance());
    }

    private WallOfSwords(final WallOfSwords card) {
        super(card);
    }

    @Override
    public WallOfSwords copy() {
        return new WallOfSwords(this);
    }
}
