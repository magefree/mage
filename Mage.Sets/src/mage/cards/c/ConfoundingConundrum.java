package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ConfoundingConundrum extends CardImpl {

    private static final FilterPermanent filter = new FilterLandPermanent();

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public ConfoundingConundrum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        // When Confounding Conundrum enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // Whenever a land enters the battlefield under an opponent's control, if that player had another land enter the battlefield under their control this turn, they return a land they control to its owner's hand.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD, new ConfoundingConundrumEffect(), filter,
                false, SetTargetPointer.PLAYER, ""
        ), ConfoundingConundrumCondition.instance, "Whenever a land enters the battlefield under " +
                "an opponent's control, if that player had another land enter the battlefield " +
                "under their control this turn, they return a land they control to its owner's hand."
        ), new ConfoundingConundrumWatcher());
    }

    private ConfoundingConundrum(final ConfoundingConundrum card) {
        super(card);
    }

    @Override
    public ConfoundingConundrum copy() {
        return new ConfoundingConundrum(this);
    }
}

enum ConfoundingConundrumCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        ConfoundingConundrumWatcher watcher = game.getState().getWatcher(ConfoundingConundrumWatcher.class);
        if (watcher == null) {
            return false;
        }
        Player player = null;
        for (Effect effect : source.getEffects()) {
            if (player != null) {
                break;
            }
            player = game.getPlayer(effect.getTargetPointer().getFirst(game, source));
        }
        return player != null && watcher.checkPlayer(player.getId());
    }
}

class ConfoundingConundrumEffect extends OneShotEffect {

    ConfoundingConundrumEffect() {
        super(Outcome.Benefit);
    }

    private ConfoundingConundrumEffect(final ConfoundingConundrumEffect effect) {
        super(effect);
    }

    @Override
    public ConfoundingConundrumEffect copy() {
        return new ConfoundingConundrumEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(1, StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND);
        target.setNotTarget(true);
        if (!target.canChoose(source.getSourceId(), player.getId(), game)) {
            return false;
        }
        player.choose(outcome, target, source.getSourceId(), game);
        return player.moveCards(game.getCard(target.getFirstTarget()), Zone.HAND, source, game);
    }
}

class ConfoundingConundrumWatcher extends Watcher {

    private final Map<UUID, Integer> playerMap = new HashMap<>();

    ConfoundingConundrumWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            return;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null && permanent.isLand(game)) {
            playerMap.compute(permanent.getControllerId(), CardUtil::setOrIncrementValue);
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerMap.clear();
    }

    boolean checkPlayer(UUID playerId) {
        return playerMap.getOrDefault(playerId, 0) > 1;
    }
}
