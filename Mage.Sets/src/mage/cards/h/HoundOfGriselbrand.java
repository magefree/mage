
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.UndyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class HoundOfGriselbrand extends CardImpl {

    public HoundOfGriselbrand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.DOG);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(DoubleStrikeAbility.getInstance());
        this.addAbility(new UndyingAbility());
    }

    private HoundOfGriselbrand(final HoundOfGriselbrand card) {
        super(card);
    }

    @Override
    public HoundOfGriselbrand copy() {
        return new HoundOfGriselbrand(this);
    }
}
