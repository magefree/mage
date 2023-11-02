

package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ExaltedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class OutriderOfJhess extends CardImpl {

    public OutriderOfJhess (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(new ExaltedAbility());
    }

    private OutriderOfJhess(final OutriderOfJhess card) {
        super(card);
    }

    @Override
    public OutriderOfJhess copy() {
        return new OutriderOfJhess(this);
    }
}
