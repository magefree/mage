package mage.cards.w;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public final class WaterspoutWarden extends CardImpl {

    public WaterspoutWarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Waterspout Warden attacks, if another creature entered the battlefield under your control this turn, Waterspout Warden gains flying until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new GainAbilitySourceEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn
        )).withInterveningIf(WaterspoutWardenCondition.instance), new WaterspoutWardenWatcher());
    }

    private WaterspoutWarden(final WaterspoutWarden card) {
        super(card);
    }

    @Override
    public WaterspoutWarden copy() {
        return new WaterspoutWarden(this);
    }
}

enum WaterspoutWardenCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return WaterspoutWardenWatcher.checkPermanent(game, source);
    }

    @Override
    public String toString() {
        return "another creature entered the battlefield under your control this turn";
    }
}

class WaterspoutWardenWatcher extends Watcher {

    // Key: Player id
    // Value: set of all creatures that entered under that player's control this turn
    private final Map<UUID, Set<MageObjectReference>> map = new HashMap<>();

    WaterspoutWardenWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            return;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null) {
            map.computeIfAbsent(permanent.getControllerId(), x -> new HashSet<>())
                    .add(new MageObjectReference(permanent, game));
        }

    }

    @Override
    public void reset() {
        map.clear();
        super.reset();
    }

    static boolean checkPermanent(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(WaterspoutWardenWatcher.class)
                .map
                .getOrDefault(source.getControllerId(), Collections.emptySet())
                .stream()
                .anyMatch(mor -> !mor.refersTo(source, game));
    }
}
