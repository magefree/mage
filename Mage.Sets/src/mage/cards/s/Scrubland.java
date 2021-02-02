
package mage.cards.s;

import java.util.UUID;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class Scrubland extends CardImpl {

    public Scrubland(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.subtype.add(SubType.PLAINS);
        this.subtype.add(SubType.SWAMP);
        
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlackManaAbility());
    }

    private Scrubland(final Scrubland card) {
        super(card);
    }

    @Override
    public Scrubland copy() {
        return new Scrubland(this);
    }
}
