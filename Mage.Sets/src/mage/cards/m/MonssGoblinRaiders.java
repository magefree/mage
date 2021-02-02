
package mage.cards.m;

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
public final class MonssGoblinRaiders extends CardImpl {

    public MonssGoblinRaiders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
    }

    private MonssGoblinRaiders(final MonssGoblinRaiders card) {
        super(card);
    }

    @Override
    public MonssGoblinRaiders copy() {
        return new MonssGoblinRaiders(this);
    }
}
