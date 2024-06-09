
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.RampageAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author LoneFox
 */
public final class HundingGjornersen extends CardImpl {

    public HundingGjornersen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Rampage 1
        this.addAbility(new RampageAbility(1));
    }

    private HundingGjornersen(final HundingGjornersen card) {
        super(card);
    }

    @Override
    public HundingGjornersen copy() {
        return new HundingGjornersen(this);
    }
}
