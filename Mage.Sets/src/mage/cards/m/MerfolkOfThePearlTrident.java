
package mage.cards.m;

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
public final class MerfolkOfThePearlTrident extends CardImpl {

    public MerfolkOfThePearlTrident(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}");
        this.subtype.add(SubType.MERFOLK);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
    }

    private MerfolkOfThePearlTrident(final MerfolkOfThePearlTrident card) {
        super(card);
    }

    @Override
    public MerfolkOfThePearlTrident copy() {
        return new MerfolkOfThePearlTrident(this);
    }
}
