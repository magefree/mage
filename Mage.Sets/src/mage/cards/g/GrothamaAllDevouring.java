package mage.cards.g;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.Watcher;

/**
 *
 * @author TheElk801
 */
public final class GrothamaAllDevouring extends CardImpl {

    public GrothamaAllDevouring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(10);
        this.toughness = new MageInt(8);

        // Other creatures have "Whenever this creature attacks, you may have it fight Grothama, All-Devouring."
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GrothamaAllDevouringGainAbilityEffect()));

        // When Grothama leaves the battlefield, each player draws cards equal to the amount of damage dealt to Grothama this turn by sources they controlled.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new GrothamaAllDevouringDrawCardsEffect(), false), new GrothamaAllDevouringWatcher());
    }

    private GrothamaAllDevouring(final GrothamaAllDevouring card) {
        super(card);
    }

    @Override
    public GrothamaAllDevouring copy() {
        return new GrothamaAllDevouring(this);
    }
}

class GrothamaAllDevouringGainAbilityEffect extends GainAbilityAllEffect {

    private static final FilterCreaturePermanent otherCreatureFilter = new FilterCreaturePermanent("other creatures");

    static {
        otherCreatureFilter.add(AnotherPredicate.instance);
    }

    GrothamaAllDevouringGainAbilityEffect() {
        super(new AttacksTriggeredAbility(
                new GrothamaAllDevouringFightEffect(null, null), true
        ), Duration.WhileOnBattlefield, otherCreatureFilter);
        this.staticText = "Other creatures have \"Whenever this creature attacks, you may have it fight {this}.\"";
    }

    GrothamaAllDevouringGainAbilityEffect(final GrothamaAllDevouringGainAbilityEffect effect) {
        super(effect);
    }

    @Override
    public GrothamaAllDevouringGainAbilityEffect copy() {
        return new GrothamaAllDevouringGainAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        ability = new AttacksTriggeredAbility(new GrothamaAllDevouringFightEffect(permanent.getId(), permanent.getName()), true);
        return super.apply(game, source);
    }
}

class GrothamaAllDevouringFightEffect extends OneShotEffect {

    private final UUID fightId;

    GrothamaAllDevouringFightEffect(UUID fightId, String fightName) {
        super(Outcome.Benefit);
        this.fightId = fightId;
        this.staticText = "you may have it fight " + fightName;
    }

    GrothamaAllDevouringFightEffect(final GrothamaAllDevouringFightEffect effect) {
        super(effect);
        this.fightId = effect.fightId;
    }

    @Override
    public GrothamaAllDevouringFightEffect copy() {
        return new GrothamaAllDevouringFightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent grothama = game.getPermanent(fightId);
        Permanent creature = game.getPermanent(source.getSourceId());
        if (grothama == null || creature == null) {
            return false;
        }
        return grothama.fight(creature, source, game);
    }
}

class GrothamaAllDevouringDrawCardsEffect extends OneShotEffect {

    GrothamaAllDevouringDrawCardsEffect() {
        super(Outcome.Benefit);
        this.staticText = "each player draws cards equal to the amount of "
                + "damage dealt to {this} this turn by sources they controlled.";
    }

    GrothamaAllDevouringDrawCardsEffect(final GrothamaAllDevouringDrawCardsEffect effect) {
        super(effect);
    }

    @Override
    public GrothamaAllDevouringDrawCardsEffect copy() {
        return new GrothamaAllDevouringDrawCardsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        GrothamaAllDevouringWatcher watcher = game.getState().getWatcher(GrothamaAllDevouringWatcher.class);
        if (watcher == null) {
            return false;
        }
        Map<UUID, Integer> damageMap = watcher.getDamageMap(new MageObjectReference(source.getSourceId(), source.getSourceObjectZoneChangeCounter() - 1, game));
        for (UUID playerId : game.getPlayerList()) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                int toDraw = damageMap.getOrDefault(player.getId(), 0);
                if (toDraw > 0) {
                    player.drawCards(toDraw, source, game);
                }
            }
        }
        return true;
    }
}

class GrothamaAllDevouringWatcher extends Watcher {

    private Map<MageObjectReference, Map<UUID, Integer>> damageMap = new HashMap<>();

    GrothamaAllDevouringWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.DAMAGED_PERMANENT) {
            return;
        }
        UUID damageControllerId = game.getControllerId(event.getSourceId());
        Permanent damaged = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (damaged == null || !damaged.isCreature(game) || damageControllerId == null) {
            return;
        }
        MageObjectReference mor = new MageObjectReference(damaged, game);
        damageMap.putIfAbsent(mor, new HashMap<>());
        damageMap.get(mor).putIfAbsent(damageControllerId, 0);
        damageMap.get(mor).compute(damageControllerId, (k, damage) -> damage + event.getAmount());
    }

    @Override
    public void reset() {
        super.reset();
        damageMap.clear();
    }

    public Map<UUID, Integer> getDamageMap(MageObjectReference mor) {
        return damageMap.getOrDefault(mor, new HashMap<>());
    }
}
