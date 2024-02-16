
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class SwarmOfRats extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Rats you control");

    static{
        filter.add(SubType.RAT.getPredicate());
    }

    public SwarmOfRats(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.RAT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Swarm of Rats's power is equal to the number of Rats you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerSourceEffect(new PermanentsOnBattlefieldCount(filter))));
    }

    private SwarmOfRats(final SwarmOfRats card) {
        super(card);
    }

    @Override
    public SwarmOfRats copy() {
        return new SwarmOfRats(this);
    }
}
