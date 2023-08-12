package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public final class AnowonTheRuinThief extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.ROGUE, "Rogues");

    public AnowonTheRuinThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Other Rogues you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(
                new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, true)
        ));

        // Whenever one or more Rogues you control deal combat damage to a player, that player mills a card for each 1 damage dealt to them. If the player mills at least one creature card this way, you draw a card.
        this.addAbility(new AnowonTheRuinThiefTriggeredAbility());
    }

    private AnowonTheRuinThief(final AnowonTheRuinThief card) {
        super(card);
    }

    @Override
    public AnowonTheRuinThief copy() {
        return new AnowonTheRuinThief(this);
    }
}

class AnowonTheRuinThiefTriggeredAbility extends TriggeredAbilityImpl {

    private final Set<UUID> damagedPlayerIds = new HashSet<>();

    AnowonTheRuinThiefTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AnowonTheRuinThiefEffect(), false);
        this.addWatcher(new AnowonTheRuinThiefWatcher());
    }

    private AnowonTheRuinThiefTriggeredAbility(final AnowonTheRuinThiefTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AnowonTheRuinThiefTriggeredAbility copy() {
        return new AnowonTheRuinThiefTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                || event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_POST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_POST) {
            damagedPlayerIds.clear();
            return false;
        }
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                && ((DamagedPlayerEvent) event).isCombatDamage()) {
            Permanent creature = game.getPermanent(event.getSourceId());
            if (creature != null && creature.isControlledBy(controllerId)
                    && creature.hasSubtype(SubType.ROGUE, game)
                    && !damagedPlayerIds.contains(event.getTargetId())) {
                damagedPlayerIds.add(event.getTargetId());
                this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more Rogues you control deal combat damage to a player, " +
                "that player mills a card for each 1 damage dealt to them. " +
                "If the player mills at least one creature card this way, you draw a card.";
    }
}

class AnowonTheRuinThiefEffect extends OneShotEffect {

    AnowonTheRuinThiefEffect() {
        super(Outcome.Benefit);
    }

    private AnowonTheRuinThiefEffect(final AnowonTheRuinThiefEffect effect) {
        super(effect);
    }

    @Override
    public AnowonTheRuinThiefEffect copy() {
        return new AnowonTheRuinThiefEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        AnowonTheRuinThiefWatcher watcher = game.getState().getWatcher(AnowonTheRuinThiefWatcher.class);
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (watcher == null || player == null) {
            return false;
        }
        int damage = watcher.getDamage(player.getId(), source.getControllerId());
        if (player.millCards(damage, source, game).count(StaticFilters.FILTER_CARD_CREATURE, game) > 0) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                controller.drawCards(1, source, game);
            }
        }
        return true;
    }
}

class AnowonTheRuinThiefWatcher extends Watcher {

    private final Map<UUID, Map<UUID, Integer>> damageMap = new HashMap<>();

    AnowonTheRuinThiefWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_POST
                || event.getType() == GameEvent.EventType.CLEANUP_STEP_POST) {
            damageMap.clear();
            return;
        }
        if (event.getType() != GameEvent.EventType.DAMAGED_PLAYER
                || !((DamagedPlayerEvent) event).isCombatDamage()) {
            return;
        }
        Permanent creature = game.getPermanent(event.getSourceId());
        if (creature == null || !creature.hasSubtype(SubType.ROGUE, game)) {
            return;
        }
        damageMap
                .computeIfAbsent(event.getTargetId(), x -> new HashMap<>())
                .compute(creature.getControllerId(), (uuid, i) -> i == null ? event.getAmount() : event.getAmount() + i);
    }

    public int getDamage(UUID damagedPlayerId, UUID controllerId) {
        if (!damageMap.containsKey(damagedPlayerId)) {
            return 0;
        }
        return damageMap.get(damagedPlayerId).getOrDefault(controllerId, 0);
    }
}
