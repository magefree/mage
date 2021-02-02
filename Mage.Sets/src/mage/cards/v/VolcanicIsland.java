
package mage.cards.v;

import java.util.UUID;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class VolcanicIsland extends CardImpl {

    public VolcanicIsland(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.subtype.add(SubType.ISLAND);
        this.subtype.add(SubType.MOUNTAIN);
        
        this.addAbility(new BlueManaAbility());
        this.addAbility(new RedManaAbility());
    }

    private VolcanicIsland(final VolcanicIsland card) {
        super(card);
    }

    @Override
    public VolcanicIsland copy() {
        return new VolcanicIsland(this);
    }
}
