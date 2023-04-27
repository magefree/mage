package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TinybonesTrinketThief extends CardImpl {

    public TinybonesTrinketThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SKELETON);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // At the beginning of each end step, if an opponent discarded a card this turn, you draw a card and you lose 1 life.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(
                        new DrawCardSourceControllerEffect(1),
                        TargetController.EACH_PLAYER, false
                ), TinybonesTrinketThiefCondition.instance, "At the beginning of each end step, " +
                "if an opponent discarded a card this turn, you draw a card and you lose 1 life."
        );
        ability.addEffect(new LoseLifeSourceControllerEffect(1));
        this.addAbility(ability, new TinybonesTrinketThiefWatcher());

        // {4}{B}{B}: Each opponent with no cards in hand loses 10 life.
        this.addAbility(new SimpleActivatedAbility(new TinybonesTrinketThiefEffect(), new ManaCostsImpl<>("{4}{B}{B}")));
    }

    private TinybonesTrinketThief(final TinybonesTrinketThief card) {
        super(card);
    }

    @Override
    public TinybonesTrinketThief copy() {
        return new TinybonesTrinketThief(this);
    }
}

enum TinybonesTrinketThiefCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        TinybonesTrinketThiefWatcher watcher = game.getState().getWatcher(TinybonesTrinketThiefWatcher.class);
        return watcher != null && watcher.checkPlayer(source.getControllerId());
    }
}

class TinybonesTrinketThiefWatcher extends Watcher {

    private final Set<UUID> playerSet = new HashSet<>();

    TinybonesTrinketThiefWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DISCARDED_CARD) {
            playerSet.addAll(game.getOpponents(event.getPlayerId()));
        }
    }

    @Override
    public void reset() {
        playerSet.clear();
        super.reset();
    }

    boolean checkPlayer(UUID playerId) {
        return playerSet.contains(playerId);
    }
}

class TinybonesTrinketThiefEffect extends OneShotEffect {

    TinybonesTrinketThiefEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent with no cards in hand loses 10 life";
    }

    private TinybonesTrinketThiefEffect(final TinybonesTrinketThiefEffect effect) {
        super(effect);
    }

    @Override
    public TinybonesTrinketThiefEffect copy() {
        return new TinybonesTrinketThiefEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player != null && player.getHand().isEmpty()) {
                player.loseLife(10, game, source, false);
            }
        }
        return true;
    }
}
// tiny bones comin out my
// tiny bones comin out my mouth
