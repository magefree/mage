
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class ArdentMilitia extends CardImpl {

    public ArdentMilitia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        this.addAbility(VigilanceAbility.getInstance());
    }

    private ArdentMilitia(final ArdentMilitia card) {
        super(card);
    }

    @Override
    public ArdentMilitia copy() {
        return new ArdentMilitia(this);
    }
}
