
package mage.cards.b;

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
public final class BorderPatrol extends CardImpl {

    public BorderPatrol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.HUMAN, SubType.NOMAD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(6);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
    }

    private BorderPatrol(final BorderPatrol card) {
        super(card);
    }

    @Override
    public BorderPatrol copy() {
        return new BorderPatrol(this);
    }
}
