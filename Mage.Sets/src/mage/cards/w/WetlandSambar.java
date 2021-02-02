
package mage.cards.w;

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
public final class WetlandSambar extends CardImpl {

    public WetlandSambar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.ELK);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
    }

    private WetlandSambar(final WetlandSambar card) {
        super(card);
    }

    @Override
    public WetlandSambar copy() {
        return new WetlandSambar(this);
    }
}
