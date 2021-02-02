
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class SlipperyBogle extends CardImpl {

    public SlipperyBogle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G/U}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(HexproofAbility.getInstance());
    }

    private SlipperyBogle(final SlipperyBogle card) {
        super(card);
    }

    @Override
    public SlipperyBogle copy() {
        return new SlipperyBogle(this);
    }
}
