

package mage.cards.j;

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
public final class JwariScuttler extends CardImpl {

    public JwariScuttler (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.CRAB);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
    }

    private JwariScuttler(final JwariScuttler card) {
        super(card);
    }

    @Override
    public JwariScuttler copy() {
        return new JwariScuttler(this);
    }

}
