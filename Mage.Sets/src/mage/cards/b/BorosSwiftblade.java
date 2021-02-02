
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class BorosSwiftblade extends CardImpl {

    public BorosSwiftblade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{W}");
        this.subtype.add(SubType.HUMAN, SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        this.addAbility(DoubleStrikeAbility.getInstance());
    }

    private BorosSwiftblade(final BorosSwiftblade card) {
        super(card);
    }

    @Override
    public BorosSwiftblade copy() {
        return new BorosSwiftblade(this);
    }
}
