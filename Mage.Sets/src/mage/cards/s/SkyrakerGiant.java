
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class SkyrakerGiant extends CardImpl {

    public SkyrakerGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());
    }

    private SkyrakerGiant(final SkyrakerGiant card) {
        super(card);
    }

    @Override
    public SkyrakerGiant copy() {
        return new SkyrakerGiant(this);
    }
}
