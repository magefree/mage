

package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class NetherHorror extends CardImpl {

    public NetherHorror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

    }

    private NetherHorror(final NetherHorror card) {
        super(card);
    }

    @Override
    public NetherHorror copy() {
        return new NetherHorror(this);
    }

}
