
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class SpireMonitor extends CardImpl {

    public SpireMonitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.DRAKE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(FlashAbility.getInstance());
        this.addAbility(FlyingAbility.getInstance());
    }

    private SpireMonitor(final SpireMonitor card) {
        super(card);
    }

    @Override
    public SpireMonitor copy() {
        return new SpireMonitor(this);
    }
}
