
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author nantuko
 */
public final class ThrabenMilitia extends CardImpl {

    public ThrabenMilitia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.color.setWhite(true);

        // this card is the second face of double-faced card
        this.nightCard = true;

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        this.addAbility(TrampleAbility.getInstance());
    }

    private ThrabenMilitia(final ThrabenMilitia card) {
        super(card);
    }

    @Override
    public ThrabenMilitia copy() {
        return new ThrabenMilitia(this);
    }
}
