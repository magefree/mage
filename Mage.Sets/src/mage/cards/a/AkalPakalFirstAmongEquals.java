package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.hint.ConditionHint;
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
 * @author Susucr
 */
public final class AkalPakalFirstAmongEquals extends CardImpl {

    public AkalPakalFirstAmongEquals(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        // At the beginning of each player's end step, if an artifact entered the battlefield under your control this turn, look at the top two cards of your library. Put one of them into your hand and the other into your graveyard.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new LookLibraryAndPickControllerEffect(2, 1, PutCards.HAND, PutCards.GRAVEYARD),
                TargetController.EACH_PLAYER,
                AkalPakalCondition.instance,
                false
        );
        ability.addHint(new ConditionHint(AkalPakalCondition.instance));
        this.addAbility(ability, new AkalPakalWatcher());
    }

    private AkalPakalFirstAmongEquals(final AkalPakalFirstAmongEquals card) {
        super(card);
    }

    @Override
    public AkalPakalFirstAmongEquals copy() {
        return new AkalPakalFirstAmongEquals(this);
    }
}

enum AkalPakalCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return AkalPakalWatcher.checkPlayer(source.getControllerId(), game);
    }

    @Override
    public String toString() {
        return "an artifact entered the battlefield under your control this turn";
    }
}

class AkalPakalWatcher extends Watcher {

    // Set of the players that had an artifact enter this turn.
    private final Set<UUID> playerSet = new HashSet<>();

    AkalPakalWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            return;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null && permanent.isArtifact(game)) {
            playerSet.add(event.getPlayerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerSet.clear();
    }

    static boolean checkPlayer(UUID playerId, Game game) {
        AkalPakalWatcher watcher = game.getState().getWatcher(AkalPakalWatcher.class);
        return watcher != null && watcher.playerSet.contains(playerId);
    }

}
