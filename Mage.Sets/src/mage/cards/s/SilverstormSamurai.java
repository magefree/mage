
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.BushidoAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class SilverstormSamurai extends CardImpl {

    public SilverstormSamurai(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}{W}");
        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.SAMURAI);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(FlashAbility.getInstance());
        this.addAbility(new BushidoAbility(1));
    }

    private SilverstormSamurai(final SilverstormSamurai card) {
        super(card);
    }

    @Override
    public SilverstormSamurai copy() {
        return new SilverstormSamurai(this);
    }
}
