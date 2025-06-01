package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LordJyscalGuado extends CardImpl {

    public LordJyscalGuado(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of each end step, if you put a counter on a creature this turn, investigate.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(TargetController.ANY, new InvestigateEffect(), false)
                        .withInterveningIf(LordJyscalGuadoCondition.instance)
                        .addHint(LordJyscalGuadoCondition.getHint()),
                new LordJyscalGuadoWatcher()
        );
    }

    private LordJyscalGuado(final LordJyscalGuado card) {
        super(card);
    }

    @Override
    public LordJyscalGuado copy() {
        return new LordJyscalGuado(this);
    }
}

enum LordJyscalGuadoCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(instance);

    @Override
    public boolean apply(Game game, Ability source) {
        return LordJyscalGuadoWatcher.checkPlayer(source.getControllerId(), game);
    }

    @Override
    public String toString() {
        return "you put a counter on a creature this turn";
    }

    public static Hint getHint() {
        return hint;
    }
}

class LordJyscalGuadoWatcher extends Watcher {

    Set<UUID> set = new HashSet<>();

    LordJyscalGuadoWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.COUNTER_ADDED) {
            return;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null && permanent.isCreature(game)) {
            set.add(event.getPlayerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        set.clear();
    }

    static boolean checkPlayer(UUID playerId, Game game) {
        return game.getState().getWatcher(LordJyscalGuadoWatcher.class).set.contains(playerId);
    }
}
