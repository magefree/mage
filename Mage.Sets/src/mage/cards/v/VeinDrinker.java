
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageAndDiedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageEachOtherEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class VeinDrinker extends CardImpl {

    public VeinDrinker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}");
        this.subtype.add(SubType.VAMPIRE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {R}, {tap}: Vein Drinker deals damage equal to its power to target creature. That creature deals damage equal to its power to Vein Drinker.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageEachOtherEffect(), new ManaCostsImpl<>("{R}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        // Whenever a creature dealt damage by Vein Drinker this turn dies, put a +1/+1 counter on Vein Drinker.
        this.addAbility(new DealtDamageAndDiedTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));
    }

    private VeinDrinker(final VeinDrinker card) {
        super(card);
    }

    @Override
    public VeinDrinker copy() {
        return new VeinDrinker(this);
    }
}
