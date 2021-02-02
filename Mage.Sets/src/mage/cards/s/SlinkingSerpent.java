
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ForestwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class SlinkingSerpent extends CardImpl {

    public SlinkingSerpent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{B}");
        this.subtype.add(SubType.SERPENT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        this.addAbility(new ForestwalkAbility());
    }

    private SlinkingSerpent(final SlinkingSerpent card) {
        super(card);
    }

    @Override
    public SlinkingSerpent copy() {
        return new SlinkingSerpent(this);
    }
}
