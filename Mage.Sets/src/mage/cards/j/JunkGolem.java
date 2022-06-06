
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;

/**
 *
 * @author TheElk801
 */
public final class JunkGolem extends CardImpl {

    public JunkGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Junk Golem enters the battlefield with three +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)), "with three +1/+1 counters on it"));

        // At the beginning of your upkeep, sacrifice Junk Golem unless you remove a +1/+1 counter from it.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new SacrificeSourceUnlessPaysEffect(
                        new RemoveCountersSourceCost(CounterType.P1P1.createInstance())
                ), TargetController.YOU, false
        ));

        // {1}, Discard a card: Put a +1/+1 counter on Junk Golem.
        Ability ability = new SimpleActivatedAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new ManaCostsImpl<>("{1}"));
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private JunkGolem(final JunkGolem card) {
        super(card);
    }

    @Override
    public JunkGolem copy() {
        return new JunkGolem(this);
    }
}
