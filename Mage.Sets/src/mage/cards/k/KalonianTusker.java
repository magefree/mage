
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class KalonianTusker extends CardImpl {

    public KalonianTusker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
    }

    private KalonianTusker(final KalonianTusker card) {
        super(card);
    }

    @Override
    public KalonianTusker copy() {
        return new KalonianTusker(this);
    }
}
