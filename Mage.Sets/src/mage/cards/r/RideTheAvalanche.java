package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RideTheAvalanche extends CardImpl {

    public RideTheAvalanche(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}{U}");

        // The next spell you cast this turn can be cast as though it had flash. When you cast your next spell this turn, put X +1/+1 counters on up to one target creature, where X is the mana value of that spell.
        this.getSpellAbility().addEffect(new RideTheAvalancheAsThoughEffect());
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new RideTheAvalancheTriggeredAbility()));
        this.getSpellAbility().addWatcher(new RideTheAvalancheWatcher());
    }

    private RideTheAvalanche(final RideTheAvalanche card) {
        super(card);
    }

    @Override
    public RideTheAvalanche copy() {
        return new RideTheAvalanche(this);
    }
}

class RideTheAvalancheAsThoughEffect extends AsThoughEffectImpl {

    public RideTheAvalancheAsThoughEffect() {
        super(AsThoughEffectType.CAST_AS_INSTANT, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "The next spell you cast this turn can be cast as though it had flash";
    }

    private RideTheAvalancheAsThoughEffect(final RideTheAvalancheAsThoughEffect effect) {
        super(effect);
    }

    @Override
    public void init(Ability source, Game game) {
        RideTheAvalancheWatcher.addPlayer(source.getControllerId(), game);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public RideTheAvalancheAsThoughEffect copy() {
        return new RideTheAvalancheAsThoughEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (!source.isControlledBy(affectedControllerId)) {
            return false;
        }
        if (!RideTheAvalancheWatcher.checkPlayer(affectedControllerId, game)) {
            discard();
            return false;
        }
        Card card = game.getCard(sourceId);
        return card != null;
    }
}

class RideTheAvalancheWatcher extends Watcher {

    private final Set<UUID> playerSet = new HashSet<>();

    public RideTheAvalancheWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            playerSet.remove(event.getPlayerId());
        }
    }

    public static void addPlayer(UUID playerId, Game game) {
        game.getState().getWatcher(RideTheAvalancheWatcher.class).playerSet.add(playerId);
    }

    public static boolean checkPlayer(UUID playerId, Game game) {
        return game.getState().getWatcher(RideTheAvalancheWatcher.class).playerSet.contains(playerId);
    }

    @Override
    public void reset() {
        super.reset();
        playerSet.clear();
    }
}

class RideTheAvalancheTriggeredAbility extends DelayedTriggeredAbility {

    public RideTheAvalancheTriggeredAbility() {
        super(null, Duration.EndOfTurn, true, false);
        this.addTarget(new TargetCreaturePermanent(0, 1));
    }

    private RideTheAvalancheTriggeredAbility(final RideTheAvalancheTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            return false;
        }
        Spell spell = game.getSpell(event.getTargetId());
        if (spell == null) {
            return false;
        }
        this.getEffects().clear();
        this.addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(spell.getManaValue())));
        return true;
    }

    @Override
    public RideTheAvalancheTriggeredAbility copy() {
        return new RideTheAvalancheTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When you cast your next spell this turn, " +
                "put X +1/+1 counters on up to one target creature, " +
                "where X is the mana value of that spell.";
    }
}