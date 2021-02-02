
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class CyclopsOfOneEyedPass extends CardImpl {

    public CyclopsOfOneEyedPass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.CYCLOPS);

        this.power = new MageInt(5);
        this.toughness = new MageInt(2);
    }

    private CyclopsOfOneEyedPass(final CyclopsOfOneEyedPass card) {
        super(card);
    }

    @Override
    public CyclopsOfOneEyedPass copy() {
        return new CyclopsOfOneEyedPass(this);
    }
}
