
package mage.cards.u;

import java.util.UUID;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class UndergroundSea extends CardImpl {

    public UndergroundSea(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.subtype.add(SubType.ISLAND);
        this.subtype.add(SubType.SWAMP);
        
        this.addAbility(new BlueManaAbility());
        this.addAbility(new BlackManaAbility());
    }

    private UndergroundSea(final UndergroundSea card) {
        super(card);
    }

    @Override
    public UndergroundSea copy() {
        return new UndergroundSea(this);
    }
}
