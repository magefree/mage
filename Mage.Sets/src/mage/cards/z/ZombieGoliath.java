

package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Loki
 */
public final class ZombieGoliath extends CardImpl {

    public ZombieGoliath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.ZOMBIE, SubType.GIANT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);
    }

    private ZombieGoliath(final ZombieGoliath card) {
        super(card);
    }

    @Override
    public ZombieGoliath copy() {
        return new ZombieGoliath(this);
    }

}
