

package mage.cards.w;

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
public final class WoollyThoctar extends CardImpl {

    public WoollyThoctar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{G}{W}");


        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);
    }

    private WoollyThoctar(final WoollyThoctar card) {
        super(card);
    }

    @Override
    public WoollyThoctar copy() {
        return new WoollyThoctar(this);
    }

}
