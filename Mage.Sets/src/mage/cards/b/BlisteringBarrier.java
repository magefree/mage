
package mage.cards.b;

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
public final class BlisteringBarrier extends CardImpl {

    public BlisteringBarrier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.WALL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(2);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
    }

    private BlisteringBarrier(final BlisteringBarrier card) {
        super(card);
    }

    @Override
    public BlisteringBarrier copy() {
        return new BlisteringBarrier(this);
    }
}
