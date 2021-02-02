
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author L_J
 */
public final class CanalMonitor extends CardImpl {

    public CanalMonitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);
    }

    private CanalMonitor(final CanalMonitor card) {
        super(card);
    }

    @Override
    public CanalMonitor copy() {
        return new CanalMonitor(this);
    }
}
