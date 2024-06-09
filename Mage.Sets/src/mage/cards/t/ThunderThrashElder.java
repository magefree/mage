
package mage.cards.t;

import mage.MageInt;
import mage.abilities.keyword.DevourAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class ThunderThrashElder extends CardImpl {

    public ThunderThrashElder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.VIASHINO);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Devour 3 (As this enters the battlefield, you may sacrifice any number of creatures. This creature enters the battlefield with twice that many +1/+1 counters on it.)
        this.addAbility(new DevourAbility(3));
    }

    private ThunderThrashElder(final ThunderThrashElder card) {
        super(card);
    }

    @Override
    public ThunderThrashElder copy() {
        return new ThunderThrashElder(this);
    }
}
