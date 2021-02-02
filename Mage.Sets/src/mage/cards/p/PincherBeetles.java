
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class PincherBeetles extends CardImpl {

    public PincherBeetles(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        this.addAbility(ShroudAbility.getInstance());
    }

    private PincherBeetles(final PincherBeetles card) {
        super(card);
    }

    @Override
    public PincherBeetles copy() {
        return new PincherBeetles(this);
    }
}
