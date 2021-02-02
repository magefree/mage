
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class EbonyRhino extends CardImpl {

    public EbonyRhino(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{7}");
        this.subtype.add(SubType.RHINO);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private EbonyRhino(final EbonyRhino card) {
        super(card);
    }

    @Override
    public EbonyRhino copy() {
        return new EbonyRhino(this);
    }
}
