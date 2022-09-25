
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class DakmorSorceress extends CardImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Swamps you control");

    static {
        filter.add(SubType.SWAMP.getPredicate());
    }

    public DakmorSorceress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Dakmor Sorceress's power is equal to the number of Swamps you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerSourceEffect(new PermanentsOnBattlefieldCount(filter), Duration.EndOfGame)));
    }

    private DakmorSorceress(final DakmorSorceress card) {
        super(card);
    }

    @Override
    public DakmorSorceress copy() {
        return new DakmorSorceress(this);
    }
}
