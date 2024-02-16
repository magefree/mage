package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.watchers.Watcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AshenReaper extends CardImpl {

    public AshenReaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.color.setBlack(true);
        this.color.setRed(true);
        this.nightCard = true;

        // Menace
        this.addAbility(new MenaceAbility(false));

        // At the beginning of your end step, put a +1/+1 counter on Ashen Reaper if a permanent was put into a graveyard from the battlefield this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new ConditionalOneShotEffect(
                        new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                        AshenReaperCondition.instance, "put a +1/+1 counter on {this} " +
                        "if a permanent was put into a graveyard from the battlefield this turn"
                ), TargetController.YOU, false
        ));
    }

    private AshenReaper(final AshenReaper card) {
        super(card);
    }

    @Override
    public AshenReaper copy() {
        return new AshenReaper(this);
    }

    public static AshenReaperWatcher makeWatcher() {
        return new AshenReaperWatcher();
    }
}

enum AshenReaperCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getState().getWatcher(AshenReaperWatcher.class).conditionMet();
    }
}

class AshenReaperWatcher extends Watcher {

    AshenReaperWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE
                && ((ZoneChangeEvent) event).isDiesEvent()) {
            condition = true;
        }
    }
}
