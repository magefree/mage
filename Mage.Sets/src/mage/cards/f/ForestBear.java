
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class ForestBear extends CardImpl {

    public ForestBear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.BEAR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
    }

    private ForestBear(final ForestBear card) {
        super(card);
    }

    @Override
    public ForestBear copy() {
        return new ForestBear(this);
    }
}
