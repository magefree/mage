
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class KnightOfMeadowgrain extends CardImpl {

    public KnightOfMeadowgrain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{W}");
        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(FirstStrikeAbility.getInstance());
        this.addAbility(LifelinkAbility.getInstance());
    }

    private KnightOfMeadowgrain(final KnightOfMeadowgrain card) {
        super(card);
    }

    @Override
    public KnightOfMeadowgrain copy() {
        return new KnightOfMeadowgrain(this);
    }
}
