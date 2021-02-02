
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.SupportAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class ReliefCaptain extends CardImpl {

    public ReliefCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{W}");
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.KNIGHT);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Relief Captain enters the battlefield, support 3.
        this.addAbility(new SupportAbility(this, 3));
    }

    private ReliefCaptain(final ReliefCaptain card) {
        super(card);
    }

    @Override
    public ReliefCaptain copy() {
        return new ReliefCaptain(this);
    }
}
