
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class LudevicsAbomination extends CardImpl {

    public LudevicsAbomination(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.HORROR);
        this.color.setBlue(true);

        // this card is the second face of double-faced card
        this.nightCard = true;

        this.power = new MageInt(13);
        this.toughness = new MageInt(13);

        this.addAbility(TrampleAbility.getInstance());
    }

    private LudevicsAbomination(final LudevicsAbomination card) {
        super(card);
    }

    @Override
    public LudevicsAbomination copy() {
        return new LudevicsAbomination(this);
    }
}
