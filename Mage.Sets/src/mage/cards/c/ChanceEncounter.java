package mage.cards.c;

import mage.abilities.common.WonCoinFlipControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ChanceEncounter extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.LUCK, 10);

    public ChanceEncounter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{R}");

        // Whenever you win a coin flip, put a luck counter on Chance Encounter.
        this.addAbility(new WonCoinFlipControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.LUCK.createInstance())
        ));

        // At the beginning of your upkeep, if Chance Encounter has ten or more luck counters on it, you win the game.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new WinGameSourceControllerEffect()).withInterveningIf(condition));
    }

    private ChanceEncounter(final ChanceEncounter card) {
        super(card);
    }

    @Override
    public ChanceEncounter copy() {
        return new ChanceEncounter(this);
    }
}
