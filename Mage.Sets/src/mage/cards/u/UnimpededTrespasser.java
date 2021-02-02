
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class UnimpededTrespasser extends CardImpl {

    public UnimpededTrespasser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.color.setBlue(true);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // Unimpeded Trespasser can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());
    }

    private UnimpededTrespasser(final UnimpededTrespasser card) {
        super(card);
    }

    @Override
    public UnimpededTrespasser copy() {
        return new UnimpededTrespasser(this);
    }
}
