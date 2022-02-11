
package mage.cards.h;

import java.util.*;

import mage.MageInt;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPlayer;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public final class HopeOfGhirapur extends CardImpl {

    private static final FilterPlayer filter = new FilterPlayer("player who was dealt combat damage by {this} this turn");

    static {
        filter.add(new HopeOfGhirapurPlayerLostLifePredicate());
    }

    public HopeOfGhirapur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.THOPTER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Sacrifice Hope of Ghirapur: Until your next turn, target player who was dealt combat damage by Hope of Ghirapur this turn can't cast noncreature spells.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new HopeOfGhirapurCantCastEffect(), new SacrificeSourceCost());
        ability.addTarget(new TargetPlayer(1, 1, false, filter));
        this.addAbility(ability, new HopeOfGhirapurCombatDamageWatcher());
    }

    private HopeOfGhirapur(final HopeOfGhirapur card) {
        super(card);
    }

    @Override
    public HopeOfGhirapur copy() {
        return new HopeOfGhirapur(this);
    }
}

class HopeOfGhirapurCantCastEffect extends ContinuousRuleModifyingEffectImpl {

    public HopeOfGhirapurCantCastEffect() {
        super(Duration.UntilYourNextTurn, Outcome.Benefit);
        staticText = "Until your next turn, target player who was dealt combat damage by {this} this turn can't cast noncreature spells";
    }

    public HopeOfGhirapurCantCastEffect(final HopeOfGhirapurCantCastEffect effect) {
        super(effect);
    }

    @Override
    public HopeOfGhirapurCantCastEffect copy() {
        return new HopeOfGhirapurCantCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = source.getSourceObject(game);
        if (mageObject != null) {
            return "You can't cast noncreature spells this turn (you were dealt damage by " + mageObject.getLogName() + ')';
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player != null && player.getId().equals(event.getPlayerId())) {
            Card card = game.getCard(event.getSourceId());
            if (card != null && !card.isCreature(game)) {
                return true;
            }
        }
        return false;
    }
}

class HopeOfGhirapurPlayerLostLifePredicate implements ObjectSourcePlayerPredicate<Player> {

    public HopeOfGhirapurPlayerLostLifePredicate() {
    }

    @Override
    public boolean apply(ObjectSourcePlayer<Player> input, Game game) {
        Player targetPlayer = input.getObject();
        if (targetPlayer == null) {
            return false;
        }
        HopeOfGhirapurCombatDamageWatcher watcher = game.getState().getWatcher(HopeOfGhirapurCombatDamageWatcher.class);
        if (watcher != null) {
            return watcher.playerGotCombatDamage(input.getSourceId(), input.getObject().getId(), game);
        }
        return false;
    }
}

class HopeOfGhirapurCombatDamageWatcher extends Watcher {

    private final Map<MageObjectReference, Set<UUID>> combatDamagedPlayers = new HashMap<>();

    public HopeOfGhirapurCombatDamageWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER && ((DamagedPlayerEvent) event).isCombatDamage()) {
            MageObjectReference damager = new MageObjectReference(event.getSourceId(), game);
            Set<UUID> players;
            if (combatDamagedPlayers.containsKey(damager)) {
                players = combatDamagedPlayers.get(damager);
            } else {
                players = new HashSet<>();
                combatDamagedPlayers.put(damager, players);
            }
            players.add(event.getTargetId());
        }
    }

    /**
     * Checks if the current object has damaged the player during the current
     * turn.
     *
     * @param objectId
     * @param playerId
     * @return
     */
    public boolean playerGotCombatDamage(UUID objectId, UUID playerId, Game game) {
        StackObject stackObject = game.getState().getStack().getStackObject(objectId);
        MageObjectReference mor;
        if (stackObject instanceof StackAbility) {
            // This is neccessary because the source object was sacrificed as cost and the correct zone change counter for target calid check can only be get from stack
            mor = new MageObjectReference(objectId, ((StackAbility) stackObject).getSourceObjectZoneChangeCounter(), game);
        } else {
            mor = new MageObjectReference(objectId, game);
        }
        if (combatDamagedPlayers.containsKey(mor)) {
            return combatDamagedPlayers.get(mor).contains(playerId);
        }
        return false;
    }

    @Override
    public void reset() {
        super.reset();
        combatDamagedPlayers.clear();
    }
}
