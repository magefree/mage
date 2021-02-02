
package mage.cards.o;

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
public final class ObsianusGolem extends CardImpl {

    public ObsianusGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{6}");
        this.subtype.add(SubType.GOLEM);

        this.power = new MageInt(4);
        this.toughness = new MageInt(6);
    }

    private ObsianusGolem(final ObsianusGolem card) {
        super(card);
    }

    @Override
    public ObsianusGolem copy() {
        return new ObsianusGolem(this);
    }
}
