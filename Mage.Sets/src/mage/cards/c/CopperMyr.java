

package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class CopperMyr extends CardImpl {

    public CopperMyr (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}");
        this.subtype.add(SubType.MYR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new GreenManaAbility());
    }

    private CopperMyr(final CopperMyr card) {
        super(card);
    }

    @Override
    public CopperMyr copy() {
        return new CopperMyr(this);
    }

}
