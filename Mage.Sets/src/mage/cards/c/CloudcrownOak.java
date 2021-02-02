
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class CloudcrownOak extends CardImpl {

    public CloudcrownOak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        this.addAbility(ReachAbility.getInstance());
    }

    private CloudcrownOak(final CloudcrownOak card) {
        super(card);
    }

    @Override
    public CloudcrownOak copy() {
        return new CloudcrownOak(this);
    }
}
