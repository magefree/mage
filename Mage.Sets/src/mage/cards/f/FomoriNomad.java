
package mage.cards.f;

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
public final class FomoriNomad extends CardImpl {

    public FomoriNomad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.NOMAD);
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
    }

    private FomoriNomad(final FomoriNomad card) {
        super(card);
    }

    @Override
    public FomoriNomad copy() {
        return new FomoriNomad(this);
    }
}
