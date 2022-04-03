
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class KolaghanSkirmisher extends CardImpl {

    public KolaghanSkirmisher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Dash {2}{B}
        this.addAbility(new DashAbility("{2}{B}"));
    }

    private KolaghanSkirmisher(final KolaghanSkirmisher card) {
        super(card);
    }

    @Override
    public KolaghanSkirmisher copy() {
        return new KolaghanSkirmisher(this);
    }
}
