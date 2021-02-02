
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class DaggerbackBasilisk extends CardImpl {

    public DaggerbackBasilisk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.BASILISK);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(DeathtouchAbility.getInstance());
    }

    private DaggerbackBasilisk(final DaggerbackBasilisk card) {
        super(card);
    }

    @Override
    public DaggerbackBasilisk copy() {
        return new DaggerbackBasilisk(this);
    }
}
