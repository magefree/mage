package mage.cards.m;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.CraftAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
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
public final class MastersGuideMural extends TransformingDoubleFacedCard {

    public MastersGuideMural(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{3}{W}{U}",
                "Master's Manufactory",
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "WU"
        );

        // Master's Guide-Mural
        // When Master's Guide-Mural enters the battlefield, create a 4/4 white and blue Golem artifact creature token.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new GolemWhiteBlueToken())));

        // Craft with artifact {4}{W}{W}{U}
        this.getLeftHalfCard().addAbility(new CraftAbility("{4}{W}{W}{U}"));

        // Master's Manufactory
        // {T}: Create a 4/4 white and blue Golem artifact creature token. Activate only if Master's Manufactory or another artifact entered the battlefield under your control this turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new CreateTokenEffect(new GolemWhiteBlueToken()), new TapSourceCost(), MastersManufactoryCondition.instance
        );
        ability.addHint(MastersManufactoryCondition.getHint());
        ability.addWatcher(new MastersManufactoryWatcher());
        this.getRightHalfCard().addAbility(ability);
    }

    private MastersGuideMural(final MastersGuideMural card) {
        super(card);
    }

    @Override
    public MastersGuideMural copy() {
        return new MastersGuideMural(this);
    }
}

enum MastersManufactoryCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        MastersManufactoryWatcher watcher = game.getState().getWatcher(MastersManufactoryWatcher.class);
        return watcher != null && permanent != null
                && watcher.check(source.getControllerId(), new MageObjectReference(permanent, game));
    }

    @Override
    public String toString() {
        return "{this} or another artifact entered the battlefield under your control this turn";
    }
}

class MastersManufactoryWatcher extends Watcher {

    private final Set<UUID> playerHadArtifactEnter = new HashSet<>();
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
