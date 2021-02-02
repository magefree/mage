
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class CerodonYearling extends CardImpl {

    public CerodonYearling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{W}");
        this.subtype.add(SubType.BEAST);



        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(VigilanceAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
    }

    private CerodonYearling(final CerodonYearling card) {
        super(card);
    }

    @Override
    public CerodonYearling copy() {
        return new CerodonYearling(this);
    }
}
