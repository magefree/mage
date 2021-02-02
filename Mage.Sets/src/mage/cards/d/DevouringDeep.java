
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class DevouringDeep extends CardImpl {

    public DevouringDeep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.FISH);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Islandwalk
        this.addAbility(new IslandwalkAbility());
    }

    private DevouringDeep(final DevouringDeep card) {
        super(card);
    }

    @Override
    public DevouringDeep copy() {
        return new DevouringDeep(this);
    }
}
