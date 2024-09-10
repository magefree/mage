
package mage.cards.s;

import java.util.UUID;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author LevelX2
 */
public final class SnowCoveredForest extends CardImpl {

    public SnowCoveredForest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.supertype.add(SuperType.BASIC);
        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.FOREST);

        // G
        this.addAbility(new GreenManaAbility());
    }

    private SnowCoveredForest(final SnowCoveredForest card) {
        super(card);
    }

    @Override
    public SnowCoveredForest copy() {
        return new SnowCoveredForest(this);
    }
}
