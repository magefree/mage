package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.keyword.MyriadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class SumalaRumblers extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURE);

    public SumalaRumblers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G/W}{G/W}");
        
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Sumala Rumblers's power is equal to the number of creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerSourceEffect(xValue)
                .setText("{this}'s power is equal to the number of creatures you control")));

        // Myriad
        this.addAbility(new MyriadAbility());

    }

    private SumalaRumblers(final SumalaRumblers card) {
        super(card);
    }

    @Override
    public SumalaRumblers copy() {
        return new SumalaRumblers(this);
    }
}
