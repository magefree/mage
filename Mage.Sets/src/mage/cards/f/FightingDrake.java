
package mage.cards.f;

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
public final class FightingDrake extends CardImpl {

    public FightingDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.subtype.add(SubType.DRAKE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());
    }

    private FightingDrake(final FightingDrake card) {
        super(card);
    }

    @Override
    public FightingDrake copy() {
        return new FightingDrake(this);
    }
}
