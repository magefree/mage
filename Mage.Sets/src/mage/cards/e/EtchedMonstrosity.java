
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.TargetPlayer;

/**
 *
 * @author Loki
 */
public final class EtchedMonstrosity extends CardImpl {

    public EtchedMonstrosity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(10);
        this.toughness = new MageInt(10);
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.M1M1.createInstance(5)), " with five -1/-1 counters on it"));
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardTargetEffect(3), new ManaCostsImpl<>("{W}{U}{B}{R}{G}"));
        ability.addCost(new RemoveCountersSourceCost(CounterType.M1M1.createInstance(5)));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private EtchedMonstrosity(final EtchedMonstrosity card) {
        super(card);
    }

    @Override
    public EtchedMonstrosity copy() {
        return new EtchedMonstrosity(this);
    }

}
