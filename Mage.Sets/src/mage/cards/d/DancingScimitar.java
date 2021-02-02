
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class DancingScimitar extends CardImpl {

    public DancingScimitar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        this.addAbility(FlyingAbility.getInstance());
    }

    private DancingScimitar(final DancingScimitar card) {
        super(card);
    }

    @Override
    public DancingScimitar copy() {
        return new DancingScimitar(this);
    }
}
