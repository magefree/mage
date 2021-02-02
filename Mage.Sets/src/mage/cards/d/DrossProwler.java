
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class DrossProwler extends CardImpl {

    public DrossProwler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(FearAbility.getInstance());
    }

    private DrossProwler(final DrossProwler card) {
        super(card);
    }

    @Override
    public DrossProwler copy() {
        return new DrossProwler(this);
    }
}
