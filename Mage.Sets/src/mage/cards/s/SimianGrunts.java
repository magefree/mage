
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.EchoAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class SimianGrunts extends CardImpl {

    public SimianGrunts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.APE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Echo {2}{G}
        this.addAbility(new EchoAbility("{2}{G}"));
    }

    private SimianGrunts(final SimianGrunts card) {
        super(card);
    }

    @Override
    public SimianGrunts copy() {
        return new SimianGrunts(this);
    }
}
