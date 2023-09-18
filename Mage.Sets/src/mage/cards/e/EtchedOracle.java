
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.keyword.SunburstAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.TargetPlayer;

/**
 *
 * @author Plopman
 */
public final class EtchedOracle extends CardImpl {

    public EtchedOracle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Sunburst (This enters the battlefield with a +1/+1 counter on it for each color of mana spent to cast it.)
        this.addAbility(new SunburstAbility(this));
        // {1}, Remove four +1/+1 counters from Etched Oracle: Target player draws three cards.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardTargetEffect(3), new ManaCostsImpl<>("{1}"));
        ability.addCost(new RemoveCountersSourceCost(CounterType.P1P1.createInstance(4)));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private EtchedOracle(final EtchedOracle card) {
        super(card);
    }

    @Override
    public EtchedOracle copy() {
        return new EtchedOracle(this);
    }
}
