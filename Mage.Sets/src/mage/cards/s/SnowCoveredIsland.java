
package mage.cards.s;

import java.util.UUID;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author LevelX2
 */
public final class SnowCoveredIsland extends CardImpl {

    public SnowCoveredIsland(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.supertype.add(SuperType.BASIC);
        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.ISLAND);

        // U
        this.addAbility(new BlueManaAbility());
    }

    private SnowCoveredIsland(final SnowCoveredIsland card) {
        super(card);
    }

    @Override
    public SnowCoveredIsland copy() {
        return new SnowCoveredIsland(this);
    }
}
