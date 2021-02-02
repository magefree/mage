
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class UtopiaTree extends CardImpl {

    public UtopiaTree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.PLANT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // {tap}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private UtopiaTree(final UtopiaTree card) {
        super(card);
    }

    @Override
    public UtopiaTree copy() {
        return new UtopiaTree(this);
    }
}
