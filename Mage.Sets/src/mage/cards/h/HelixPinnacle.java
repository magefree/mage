
package mage.cards.h;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ShroudAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class HelixPinnacle extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.TOWER, 100);

    public HelixPinnacle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        // Shroud
        this.addAbility(ShroudAbility.getInstance());

        // {X}: Put X tower counters on Helix Pinnacle.
        this.addAbility(new SimpleActivatedAbility(new AddCountersSourceEffect(
                CounterType.TOWER.createInstance(), GetXValue.instance, true
        ), new ManaCostsImpl<>("{X}")));

        // At the beginning of your upkeep, if there are 100 or more tower counters on Helix Pinnacle, you win the game.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new WinGameSourceControllerEffect()).withInterveningIf(condition));
    }

    private HelixPinnacle(final HelixPinnacle card) {
        super(card);
    }

    @Override
    public HelixPinnacle copy() {
        return new HelixPinnacle(this);
    }
}
