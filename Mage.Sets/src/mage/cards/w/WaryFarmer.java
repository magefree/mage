package mage.cards.w;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.*;

/**
 *
 * @author muz
 */
public final class WaryFarmer extends CardImpl {

    public WaryFarmer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G/W}{G/W}");

        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of your end step, if another creature entered the battlefield under your control this turn, surveil 1.
        this.addAbility(
            new BeginningOfEndStepTriggeredAbility(
                new SurveilEffect(1)
            ).withInterveningIf(WaryFarmerCondition.instance),
            new WaryFarmerWatcher()
        );
    }

    private WaryFarmer(final WaryFarmer card) {
        super(card);
    }

    @Override
    public WaryFarmer copy() {
        return new WaryFarmer(this);
    }
}

enum WaryFarmerCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return WaryFarmerWatcher.checkPermanent(game, source);
    }

    @Override
    public String toString() {
        return "another creature entered the battlefield under your control this turn";
    }
}

class WaryFarmerWatcher extends Watcher {

    // Key: Player id
    // Value: set of all creatures that entered under that player's control this turn
    private final Map<UUID, Set<MageObjectReference>> map = new HashMap<>();

    WaryFarmerWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            return;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null && permanent.isCreature(game)) {
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
                .getWatcher(WaryFarmerWatcher.class)
                .map
                .getOrDefault(source.getControllerId(), Collections.emptySet())
                .stream()
                .anyMatch(mor -> !mor.refersTo(source, game));
    }
}
