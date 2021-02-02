

package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class ValiantGuard extends CardImpl {

    public ValiantGuard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(0);
    this.toughness = new MageInt(3);
    }

    private ValiantGuard(final ValiantGuard card) {
        super(card);
    }

    @Override
    public ValiantGuard copy() {
        return new ValiantGuard(this);
    }

}
