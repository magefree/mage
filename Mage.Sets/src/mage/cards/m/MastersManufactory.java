package mage.cards.m;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.GolemWhiteBlueToken;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author Susucr
 */
public final class MastersManufactory extends CardImpl {

    private static final Hint hint = new ConditionHint(
            MastersManufactoryCondition.instance,
            "{this} or another artifact entered under your control this turn"
    );

    public MastersManufactory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");
        this.nightCard = true;
        this.color.setBlue(true);
        this.color.setWhite(true);

        // {T}: Create a 4/4 white and blue Golem artifact creature token. Activate only if Master's Manufactory or another artifact entered the battlefield under your control this turn.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD,
                new CreateTokenEffect(new GolemWhiteBlueToken()),
                new TapSourceCost(),
                MastersManufactoryCondition.instance
        ).addHint(hint), new MastersManufactoryWatcher());
    }

    private MastersManufactory(final MastersManufactory card) {
        super(card);
    }

    @Override
    public MastersManufactory copy() {
        return new MastersManufactory(this);
    }
}

enum MastersManufactoryCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        MastersManufactoryWatcher watcher = game.getState().getWatcher(MastersManufactoryWatcher.class);
        return watcher != null
                && permanent != null
                && watcher.check(source.getControllerId(), new MageObjectReference(permanent, game));
    }

    @Override
    public String toString() {
        return "if {this} or another artifact entered the battlefield under your control this turn";
    }
}

class MastersManufactoryWatcher extends Watcher {

    // player -> an artifact entered this turn
    private final Set<UUID> playerHadArtifactEnter = new HashSet<>();
    // We need to store a lot for edges cases:
    //   -> Master's Manufactory could have entered as a non-artifact
    //   -> Another permanent could gain Manufactory's ability (copy effect), and we need to have tracked if it entered
    // player -> set of all MOR of permanents that entered this turn under that player's control
    private final Map<UUID, Set<MageObjectReference>> allEnteredThisTurn = new HashMap<>();

    MastersManufactoryWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
            if (permanent.isArtifact(game)) {
                playerHadArtifactEnter.add(event.getPlayerId());
            }
            allEnteredThisTurn
                    .computeIfAbsent(event.getPlayerId(), k -> new HashSet<>())
                    .add(new MageObjectReference(permanent, game));
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerHadArtifactEnter.clear();
        allEnteredThisTurn.clear();
    }

    boolean check(UUID playerId, MageObjectReference mor) {
        return playerHadArtifactEnter.contains(playerId)
                || allEnteredThisTurn.getOrDefault(playerId, Collections.emptySet()).contains(mor);
    }
}
