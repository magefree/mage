
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class BenalishKnight extends CardImpl {

    public BenalishKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN, SubType.KNIGHT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(FlashAbility.getInstance());
        this.addAbility(FirstStrikeAbility.getInstance());
    }

    private BenalishKnight(final BenalishKnight card) {
        super(card);
    }

    @Override
    public BenalishKnight copy() {
        return new BenalishKnight(this);
    }
}
