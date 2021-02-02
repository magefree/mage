
package mage.cards.t;

import java.util.UUID;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class TropicalIsland extends CardImpl {

    public TropicalIsland(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.subtype.add(SubType.FOREST);
        this.subtype.add(SubType.ISLAND);
        
        this.addAbility(new GreenManaAbility());
        this.addAbility(new BlueManaAbility());
    }

    private TropicalIsland(final TropicalIsland card) {
        super(card);
    }

    @Override
    public TropicalIsland copy() {
        return new TropicalIsland(this);
    }
}
