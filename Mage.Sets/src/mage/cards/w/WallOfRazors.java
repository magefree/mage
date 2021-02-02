
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class WallOfRazors extends CardImpl {

    public WallOfRazors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.WALL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
    }

    private WallOfRazors(final WallOfRazors card) {
        super(card);
    }

    @Override
    public WallOfRazors copy() {
        return new WallOfRazors(this);
    }
}
