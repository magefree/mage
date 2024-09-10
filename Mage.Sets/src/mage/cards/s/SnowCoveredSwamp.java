
package mage.cards.s;

import java.util.UUID;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author LevelX2
 */
public final class SnowCoveredSwamp extends CardImpl {

    public SnowCoveredSwamp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.supertype.add(SuperType.BASIC);
        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.SWAMP);

        // B
        this.addAbility(new BlackManaAbility());
    }

    private SnowCoveredSwamp(final SnowCoveredSwamp card) {
        super(card);
    }

    @Override
    public SnowCoveredSwamp copy() {
        return new SnowCoveredSwamp(this);
    }
}
