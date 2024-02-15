package mage.cards.l;

import mage.MageIdentifier;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.util.CardUtil;
import mage.watchers.Watcher;
import mage.watchers.common.PlayerAttackedWatcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LaraCroftTombRaider extends CardImpl {

    private static final FilterCard filter = new FilterCard("legendary artifact card or legendary land card from a graveyard");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    public LaraCroftTombRaider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever Lara Croft attacks, exile up to one target legendary artifact card or legendary land card from a graveyard and put a discovery counter on it. You may play a card from exile with a discovery counter on it this turn.
        Ability ability = new AttacksTriggeredAbility(new LaraCroftTombRaiderExileEffect());
        ability.addEffect(new LaraCroftTombRaiderCastEffect());
        ability.addTarget(new TargetCardInGraveyard(0, 1, filter));
        this.addAbility(ability.setIdentifier(MageIdentifier.LaraCroftTombRaiderWatcher), new LaraCroftTombRaiderWatcher());

        // Raid -- At end of combat on your turn, if you attacked this turn, create a Treasure token.
        this.addAbility(new LaraCroftTombRaiderTriggeredAbility());
    }

    private LaraCroftTombRaider(final LaraCroftTombRaider card) {
        super(card);
    }

    @Override
    public LaraCroftTombRaider copy() {
        return new LaraCroftTombRaider(this);
    }
}

class LaraCroftTombRaiderExileEffect extends OneShotEffect {

    LaraCroftTombRaiderExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile up to one target legendary artifact card " +
                "or legendary land card from a graveyard and put a discovery counter on it";
    }

    private LaraCroftTombRaiderExileEffect(final LaraCroftTombRaiderExileEffect effect) {
        super(effect);
    }

    @Override
    public LaraCroftTombRaiderExileEffect copy() {
        return new LaraCroftTombRaiderExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        card.addCounters(CounterType.DISCOVERY.createInstance(), source, game);
        return true;
    }
}

class LaraCroftTombRaiderCastEffect extends AsThoughEffectImpl {

    LaraCroftTombRaiderCastEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "you may play a card from exile with a discovery counter on it this turn";
    }

    private LaraCroftTombRaiderCastEffect(final LaraCroftTombRaiderCastEffect effect) {
        super(effect);
    }

    @Override
    public LaraCroftTombRaiderCastEffect copy() {
        return new LaraCroftTombRaiderCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        LaraCroftTombRaiderWatcher.incrementWatcher(source.getControllerId(), game);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        Card card = game.getCard(sourceId);
        return card != null
                && game.getState().getZone(sourceId) == Zone.EXILED
                && card.getCounters(game).containsKey(CounterType.DISCOVERY)
                && LaraCroftTombRaiderWatcher.checkPlayer(source.getControllerId(), game);
    }
}

class LaraCroftTombRaiderWatcher extends Watcher {

    private final Map<UUID, Integer> map = new HashMap<>();

    LaraCroftTombRaiderWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case SPELL_CAST:
            case LAND_PLAYED:
                if (event.hasApprovingIdentifier(MageIdentifier.LaraCroftTombRaiderWatcher)) {
                    map.compute(event.getPlayerId(), (u, i) -> i == null ? 1 : Integer.sum(i, -1));
                }
        }
    }

    @Override
    public void reset() {
        super.reset();
        map.clear();
    }

    static void incrementWatcher(UUID playerId, Game game) {
        game.getState()
                .getWatcher(LaraCroftTombRaiderWatcher.class)
                .map
                .compute(playerId, CardUtil::setOrIncrementValue);
    }

    static boolean checkPlayer(UUID playerId, Game game) {
        return game
                .getState()
                .getWatcher(LaraCroftTombRaiderWatcher.class)
                .map
                .getOrDefault(playerId, 0) > 0;
    }
}

class LaraCroftTombRaiderTriggeredAbility extends TriggeredAbilityImpl {

    LaraCroftTombRaiderTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new TreasureToken()));
        setTriggerPhrase("At end of combat on your turn, if you attacked this turn, ");
        this.addWatcher(new PlayerAttackedWatcher());
        this.setAbilityWord(AbilityWord.RAID);
    }

    private LaraCroftTombRaiderTriggeredAbility(final LaraCroftTombRaiderTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LaraCroftTombRaiderTriggeredAbility copy() {
        return new LaraCroftTombRaiderTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.END_COMBAT_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.isActivePlayer(getControllerId());
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return RaidCondition.instance.apply(game, this);
    }
}
