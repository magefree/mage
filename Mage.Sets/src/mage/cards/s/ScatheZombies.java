
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class ScatheZombies extends CardImpl {

    public ScatheZombies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
    }

    private ScatheZombies(final ScatheZombies card) {
        super(card);
    }

    @Override
    public ScatheZombies copy() {
        return new ScatheZombies(this);
    }
}
