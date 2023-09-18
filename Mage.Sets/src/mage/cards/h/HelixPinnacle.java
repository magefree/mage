
package mage.cards.h;

import java.util.UUID;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author jeffwadsworth
 */
public final class HelixPinnacle extends CardImpl {

    static final String rule = "at the beginning of your upkeep, if there are 100 or more tower counters on Helix Pinnacle, you win the game";

    public HelixPinnacle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        // Shroud
        this.addAbility(ShroudAbility.getInstance());

        // {X}: Put X tower counters on Helix Pinnacle.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new AddCountersSourceEffect(CounterType.TOWER.createInstance(), ManacostVariableValue.REGULAR, true),
                new ManaCostsImpl<>("{X}")));

        // At the beginning of your upkeep, if there are 100 or more tower counters on Helix Pinnacle, you win the game.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new WinGameSourceControllerEffect(), TargetController.YOU, false),
                new SourceHasCounterCondition(CounterType.TOWER, 100, Integer.MAX_VALUE),
                rule));

    }

    private HelixPinnacle(final HelixPinnacle card) {
        super(card);
    }

    @Override
    public HelixPinnacle copy() {
        return new HelixPinnacle(this);
    }
}
