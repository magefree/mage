
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Rystan
 */
public final class CabalEvangel extends CardImpl {

    public CabalEvangel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
    }

    private CabalEvangel(final CabalEvangel card) {
        super(card);
    }

    @Override
    public CabalEvangel copy() {
        return new CabalEvangel(this);
    }
}
