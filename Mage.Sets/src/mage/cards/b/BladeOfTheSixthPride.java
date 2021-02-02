
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class BladeOfTheSixthPride extends CardImpl {

    public BladeOfTheSixthPride(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.CAT, SubType.REBEL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);
    }

    private BladeOfTheSixthPride(final BladeOfTheSixthPride card) {
        super(card);
    }

    @Override
    public BladeOfTheSixthPride copy() {
        return new BladeOfTheSixthPride(this);
    }
}
