package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.watchers.common.PlayerLostLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SavageGorger extends CardImpl {

    public SavageGorger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your upkeep, if an opponent lost life this turn, put a +1/+1 counter on Savage Gorger.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(new AddCountersSourceEffect(
                        CounterType.P1P1.createInstance()), TargetController.YOU, false
                ), SavageGorgerCondition.instance, "At the beginning of your end step, " +
                "if an opponent lost life this turn, put a +1/+1 counter on {this}."
        ));
    }

    private SavageGorger(final SavageGorger card) {
        super(card);
    }

    @Override
    public SavageGorger copy() {
        return new SavageGorger(this);
    }
}

enum SavageGorgerCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        PlayerLostLifeWatcher watcher = game.getState().getWatcher(PlayerLostLifeWatcher.class);
        return watcher != null && watcher.getAllOppLifeLost(source.getControllerId(), game) > 0;
    }

    @Override
    public String toString() {
        return "";
    }
}
