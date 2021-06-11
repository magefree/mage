
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class SpinedThopter extends CardImpl {

    public SpinedThopter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}{U/P}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.THOPTER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());
    }

    private SpinedThopter(final SpinedThopter card) {
        super(card);
    }

    @Override
    public SpinedThopter copy() {
        return new SpinedThopter(this);
    }
}
