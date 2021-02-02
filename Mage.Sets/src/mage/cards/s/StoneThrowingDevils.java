
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class StoneThrowingDevils extends CardImpl {

    public StoneThrowingDevils(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.DEVIL);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
    }

    private StoneThrowingDevils(final StoneThrowingDevils card) {
        super(card);
    }

    @Override
    public StoneThrowingDevils copy() {
        return new StoneThrowingDevils(this);
    }
}
