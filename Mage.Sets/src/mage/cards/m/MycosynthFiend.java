
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.OpponentsPoisonCountersCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author North
 */
public final class MycosynthFiend extends CardImpl {

    public MycosynthFiend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        DynamicValue value = OpponentsPoisonCountersCount.instance;
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(value, value, Duration.WhileOnBattlefield)));
    }

    private MycosynthFiend(final MycosynthFiend card) {
        super(card);
    }

    @Override
    public MycosynthFiend copy() {
        return new MycosynthFiend(this);
    }
}
