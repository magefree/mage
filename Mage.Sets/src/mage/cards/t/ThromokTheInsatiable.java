
package mage.cards.t;

import mage.MageInt;
import mage.abilities.keyword.DevourAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class ThromokTheInsatiable extends CardImpl {

    public ThromokTheInsatiable(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HELLION);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Devour X, where X is the number of creatures devoured this way (As this enters the battlefield, you may sacrifice any number of creatures. This creature enters the battlefield with X +1/+1 counters on it for each of those creatures.)
        this.addAbility(DevourAbility.DevourX());
    }

    private ThromokTheInsatiable(final ThromokTheInsatiable card) {
        super(card);
    }

    @Override
    public ThromokTheInsatiable copy() {
        return new ThromokTheInsatiable(this);
    }
}
