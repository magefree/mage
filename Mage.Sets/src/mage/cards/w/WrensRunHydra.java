package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.ReinforceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WrensRunHydra extends CardImpl {

    public WrensRunHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{G}");

        this.subtype.add(SubType.HYDRA);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Wren's Run Hydra enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())));

        // Reinforce X—{X}{G}{G}
        this.addAbility(new ReinforceAbility(ManacostVariableValue.REGULAR, new ManaCostsImpl<>("{X}{G}{G}")));
    }

    private WrensRunHydra(final WrensRunHydra card) {
        super(card);
    }

    @Override
    public WrensRunHydra copy() {
        return new WrensRunHydra(this);
    }
}
