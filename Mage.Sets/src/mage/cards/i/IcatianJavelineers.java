
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author fireshoes
 */
public final class IcatianJavelineers extends CardImpl {

    public IcatianJavelineers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Icatian Javelineers enters the battlefield with a javelin counter on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.JAVELIN.createInstance()), "with a javelin counter on it"));

        // {tap}, Remove a javelin counter from Icatian Javelineers: Icatian Javelineers deals 1 damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1, "it"), new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.JAVELIN.createInstance()));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private IcatianJavelineers(final IcatianJavelineers card) {
        super(card);
    }

    @Override
    public IcatianJavelineers copy() {
        return new IcatianJavelineers(this);
    }
}
