
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
public final class WallOfHeat extends CardImpl {

    public WallOfHeat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.WALL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(6);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
    }

    private WallOfHeat(final WallOfHeat card) {
        super(card);
    }

    @Override
    public WallOfHeat copy() {
        return new WallOfHeat(this);
    }
}
