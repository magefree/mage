
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class VolunteerMilitia extends CardImpl {

    public VolunteerMilitia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);
    }

    private VolunteerMilitia(final VolunteerMilitia card) {
        super(card);
    }

    @Override
    public VolunteerMilitia copy() {
        return new VolunteerMilitia(this);
    }
}
