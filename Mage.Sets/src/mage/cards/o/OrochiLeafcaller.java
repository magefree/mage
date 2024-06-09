

package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;

/**
 *
 * @author Loki
 */
public final class OrochiLeafcaller extends CardImpl {

    public OrochiLeafcaller (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        
        // {G}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility(new ColoredManaCost(ColoredManaSymbol.G)));
    }

    private OrochiLeafcaller(final OrochiLeafcaller card) {
        super(card);
    }

    @Override
    public OrochiLeafcaller copy() {
        return new OrochiLeafcaller(this);
    }

}
