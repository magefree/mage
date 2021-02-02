
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class VineTrellis extends CardImpl {

    public VineTrellis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.WALL);

        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // {tap}: Add {G}.
        this.addAbility(new GreenManaAbility());
    }

    private VineTrellis(final VineTrellis card) {
        super(card);
    }

    @Override
    public VineTrellis copy() {
        return new VineTrellis(this);
    }
}
