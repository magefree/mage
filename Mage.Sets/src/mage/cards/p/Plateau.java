
package mage.cards.p;

import java.util.UUID;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author jonubuu
 */
public final class Plateau extends CardImpl {

    public Plateau(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.subtype.add(SubType.MOUNTAIN);
        this.subtype.add(SubType.PLAINS);
        this.addAbility(new RedManaAbility());
        this.addAbility(new WhiteManaAbility());
    }

    private Plateau(final Plateau card) {
        super(card);
    }

    @Override
    public Plateau copy() {
        return new Plateau(this);
    }
}
