
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ColorsOfManaSpentToCastCount;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class PrismArray extends CardImpl {

    public PrismArray(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{U}");

        // <i>Converge</i> &mdash; Prism Array enters the battlefield with a crystal counter on it for each color of mana spent to cast it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.CRYSTAL.createInstance(), ColorsOfManaSpentToCastCount.getInstance(), true),
                null, "<i>Converge</i> &mdash; {this} enters the battlefield with a +1/+1 counter on it for each color of mana spent to cast it.", null));

        // Remove a crystal counter from Prism Array: Tap target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new TapTargetEffect(),
                new RemoveCountersSourceCost(CounterType.CRYSTAL.createInstance(1)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {W}{U}{B}{R}{G}: Scry 3.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ScryEffect(3), new ManaCostsImpl<>("{W}{U}{B}{R}{G}")));
    }

    private PrismArray(final PrismArray card) {
        super(card);
    }

    @Override
    public PrismArray copy() {
        return new PrismArray(this);
    }
}
