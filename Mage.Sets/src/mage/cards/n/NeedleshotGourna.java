
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class NeedleshotGourna extends CardImpl {

    public NeedleshotGourna(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // Reach
        this.addAbility(ReachAbility.getInstance());
    }

    private NeedleshotGourna(final NeedleshotGourna card) {
        super(card);
    }

    @Override
    public NeedleshotGourna copy() {
        return new NeedleshotGourna(this);
    }
}
