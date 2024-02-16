
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class ZendikarIncarnate extends CardImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent("lands you control");

    public ZendikarIncarnate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{G}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);
        
        DynamicValue controlledLands = new PermanentsOnBattlefieldCount(filter);

        // Zendikar Incarnate's power is equal to the amount of lands you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerSourceEffect(controlledLands)));
    }

    private ZendikarIncarnate(final ZendikarIncarnate card) {
        super(card);
    }

    @Override
    public ZendikarIncarnate copy() {
        return new ZendikarIncarnate(this);
    }
}
