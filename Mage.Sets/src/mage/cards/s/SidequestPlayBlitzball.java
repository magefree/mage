package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EndOfCombatTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SidequestPlayBlitzball extends CardImpl {

    public SidequestPlayBlitzball(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        this.secondSideCardClazz = mage.cards.w.WorldChampionCelestialWeapon.class;

        // At the beginning of combat on your turn, target creature you control gets +2/+0 until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(new BoostTargetEffect(2, 0));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // At the end of combat on your turn, if a player was dealt 6 or more combat damage this turn, transform this enchantment, then attach it to a creature you control.
        this.addAbility(new TransformAbility());
        ability = new EndOfCombatTriggeredAbility(
                new TransformSourceEffect(), TargetController.YOU, false
        ).withInterveningIf(SidequestPlayBlitzballCondition.instance).setTriggerPhrase("At the end of combat on your turn, ");
        ability.addEffect(new SidequestPlayBlitzballEffect());
        this.addAbility(ability, new SidequestPlayBlitzballWatcher());
    }

    private SidequestPlayBlitzball(final SidequestPlayBlitzball card) {
        super(card);
    }

    @Override
    public SidequestPlayBlitzball copy() {
        return new SidequestPlayBlitzball(this);
    }
}

enum SidequestPlayBlitzballCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return SidequestPlayBlitzballWatcher.checkPlayers(game, source);
    }

    @Override
    public String toString() {
        return "a player was dealt 6 or more combat damage this turn";
    }
}

class SidequestPlayBlitzballEffect extends OneShotEffect {

    SidequestPlayBlitzballEffect() {
        super(Outcome.Benefit);
        staticText = ", then attach it to a creature you control";
    }

    private SidequestPlayBlitzballEffect(final SidequestPlayBlitzballEffect effect) {
        super(effect);
    }

    @Override
    public SidequestPlayBlitzballEffect copy() {
        return new SidequestPlayBlitzballEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null || permanent == null || !game.getBattlefield().contains(
                StaticFilters.FILTER_CONTROLLED_CREATURE, source, game, 1
        )) {
            return false;
        }
        TargetPermanent target = new TargetControlledCreaturePermanent();
        target.withNotTarget(true);
        player.choose(outcome, target, source, game);
        return Optional
                .ofNullable(target.getFirstTarget())
                .map(game::getPermanent)
                .map(p -> p.addAttachment(p.getId(), source, game))
                .orElse(false);
    }
}

class SidequestPlayBlitzballWatcher extends Watcher {

    private final Map<UUID, Integer> map = new HashMap<>();

    SidequestPlayBlitzballWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER && ((DamagedEvent) event).isCombatDamage()) {
            map.compute(event.getTargetId(), (u, i) -> i == null ? event.getAmount() : Integer.sum(i, event.getAmount()));
        }
    }

    @Override
    public void reset() {
        super.reset();
        map.clear();
    }

    private boolean checkMap(UUID playerId) {
        return map.getOrDefault(playerId, 0) >= 6;
    }

    static boolean checkPlayers(Game game, Ability source) {
        return game
                .getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .anyMatch(game.getState().getWatcher(SidequestPlayBlitzballWatcher.class)::checkMap);
    }
}
