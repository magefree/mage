
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.MountainwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class CanyonWildcat extends CardImpl {

    public CanyonWildcat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.CAT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(new MountainwalkAbility());
    }

    private CanyonWildcat(final CanyonWildcat card) {
        super(card);
    }

    @Override
    public CanyonWildcat copy() {
        return new CanyonWildcat(this);
    }
}
