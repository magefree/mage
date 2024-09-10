
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.AmplifyEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.AmplifyAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetAttackingOrBlockingCreature;

/**
 *
 * @author LoneFox
 */
public final class DaruStinger extends CardImpl {

    public DaruStinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Amplify 1
        this.addAbility(new AmplifyAbility(AmplifyEffect.AmplifyFactor.Amplify1));
        // {tap}: Daru Stinger deals damage equal to the number of +1/+1 counters on it to target attacking or blocking creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(new CountersSourceCount(CounterType.P1P1))
                .setText("{this} deals damage equal to the number of +1/+1 counters on it to target attacking or blocking creature"), new TapSourceCost());
        ability.addTarget(new TargetAttackingOrBlockingCreature());
        this.addAbility(ability);
    }

    private DaruStinger(final DaruStinger card) {
        super(card);
    }

    @Override
    public DaruStinger copy() {
        return new DaruStinger(this);
    }
}
