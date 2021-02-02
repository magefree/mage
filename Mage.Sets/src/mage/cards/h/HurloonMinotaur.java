
package mage.cards.h;

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
public final class HurloonMinotaur extends CardImpl {

    public HurloonMinotaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{R}");
        this.subtype.add(SubType.MINOTAUR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
    }

    private HurloonMinotaur(final HurloonMinotaur card) {
        super(card);
    }

    @Override
    public HurloonMinotaur copy() {
        return new HurloonMinotaur(this);
    }
}
