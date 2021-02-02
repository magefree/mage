
package mage.cards.s;

import java.util.UUID;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class Savannah extends CardImpl {

    public Savannah(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.subtype.add(SubType.FOREST);
        this.subtype.add(SubType.PLAINS);
        
        this.addAbility(new GreenManaAbility());
        this.addAbility(new WhiteManaAbility());
    }

    private Savannah(final Savannah card) {
        super(card);
    }

    @Override
    public Savannah copy() {
        return new Savannah(this);
    }
}
