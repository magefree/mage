
package mage.cards.s;

import java.util.UUID;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author LevelX2
 */
public final class SnowCoveredMountain extends CardImpl {

    public SnowCoveredMountain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.supertype.add(SuperType.BASIC);
        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.MOUNTAIN);

        // R
        this.addAbility(new RedManaAbility());
    }

    private SnowCoveredMountain(final SnowCoveredMountain card) {
        super(card);
    }

    @Override
    public SnowCoveredMountain copy() {
        return new SnowCoveredMountain(this);
    }
}
