
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.AmplifyEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.AmplifyAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author FenrisulfrX
 */
public final class KilnmouthDragon extends CardImpl {

    public KilnmouthDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{R}{R}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Amplify 3
        this.addAbility(new AmplifyAbility(AmplifyEffect.AmplifyFactor.Amplify3));
        
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // {T}: Kilnmouth Dragon deals damage equal to the number of +1/+1 counters on it to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new DamageTargetEffect(new CountersSourceCount(CounterType.P1P1))
                        .setText("{this} deals damage equal to the number of +1/+1 counters on it to any target"), new TapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private KilnmouthDragon(final KilnmouthDragon card) {
        super(card);
    }

    @Override
    public KilnmouthDragon copy() {
        return new KilnmouthDragon(this);
    }
}
