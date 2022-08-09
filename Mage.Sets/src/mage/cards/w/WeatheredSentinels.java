package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.Watcher;

import java.util.*;

/**
 * Based on Backstreet Bruiser, O-Kagachi, and Pramikon
 * @author Alex-Vasile
 */
public class WeatheredSentinels extends CardImpl {

    public WeatheredSentinels(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        addSubType(SubType.WALL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Defender, vigilance, reach, trample
        this.addAbility(DefenderAbility.getInstance());
        this.addAbility(VigilanceAbility.getInstance());
        this.addAbility(ReachAbility.getInstance());
        this.addAbility(TrampleAbility.getInstance());

        // Weathered Sentinels can attack players who attacked you during their last turn as though it didn't have defender.
        this.addAbility(new SimpleStaticAbility(
                new ConditionalAsThoughEffect(
                        new CanAttackAsThoughItDidntHaveDefenderSourceEffect(Duration.WhileOnBattlefield),
                        WeatheredSentinelsCanAttackSomeoneCondition.instance)
                        .setText("Weathered Sentinels can attack players who attacked you during their last turn as though it didn't have defender.")),
                new WeatheredSentinelsLastTurnAttackersWatcher()
        );

        // Whenever Weathered Sentinels attacks, it gets +3/+3 and gains indestructible until end of turn.
        Ability ability = new AttacksTriggeredAbility(
                new BoostSourceEffect(3, 3, Duration.EndOfTurn).setText("it gets +3/+3")
        );
        ability.addEffect(new GainAbilitySourceEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
                ).concatBy("and").setText("gains indestructible until end of turn")
        );
        this.addAbility(ability);

        // Ability to limit who Weathered Sentinels can attack
        this.addAbility(new SimpleStaticAbility(
                new WeatheredSentinelsAttackerReplacementEffect()
                ).addHint(WeatheredSentinelsPlayersWhoAttackedYouLastTurn.instance)
        );
    }

    private WeatheredSentinels(final WeatheredSentinels card) {
        super(card);
    }

    @Override
    public WeatheredSentinels copy() {
        return new WeatheredSentinels(this);
    }
}

class WeatheredSentinelsAttackerReplacementEffect extends ReplacementEffectImpl {

    WeatheredSentinelsAttackerReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
    }

    WeatheredSentinelsAttackerReplacementEffect(final WeatheredSentinelsAttackerReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARE_ATTACKER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        Player attacker = game.getPlayer(event.getPlayerId());
        if (controller == null || attacker == null) {
            return false;
        }

        if (!attacker.equals(controller)) {
            return false;
        }

        Player defender;
        if (game.getPlayer(event.getTargetId()) != null) {
            defender = game.getPlayer(event.getTargetId());
        } else {
            Permanent planeswalker = game.getPermanent(event.getTargetId());
            defender = (planeswalker == null) ? null : game.getPlayer(planeswalker.getControllerId());
        }
        if (defender == null) {
            return false;
        }

        WeatheredSentinelsLastTurnAttackersWatcher watcher = game.getState().getWatcher(WeatheredSentinelsLastTurnAttackersWatcher.class);
        if (watcher == null) {
            return false;
        }

        // Attacker and defender are supposed to be flipped here
        return watcher.checkPlayer(defender.getId(), attacker.getId());
    }

    @Override
    public ContinuousEffect copy() {
        return new WeatheredSentinelsAttackerReplacementEffect(this);
    }
}

enum WeatheredSentinelsPlayersWhoAttackedYouLastTurn implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        Player controller = game.getPlayer(ability.getControllerId());
        WeatheredSentinelsLastTurnAttackersWatcher watcher = game.getState().getWatcher(WeatheredSentinelsLastTurnAttackersWatcher.class);
        if (controller == null || watcher == null) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder("Attacked you on their last turn: ");
        Iterator<UUID> opponentIdIterator = game.getOpponents(controller.getId()).iterator();

        while (opponentIdIterator.hasNext()) {
            UUID opponentId = opponentIdIterator.next();
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null && watcher.checkPlayer(opponentId, controller.getId())) {
                stringBuilder.append(opponent.getName());
                // Add a ", " between names, but exclude adding one at the end
                if (opponentIdIterator.hasNext()) {
                    stringBuilder.append(", ");
                } else {
                    stringBuilder.append('.');
                }
            }
        }

        return stringBuilder.toString();
    }

    @Override
    public Hint copy() {
        return instance;
    }
}

enum WeatheredSentinelsCanAttackSomeoneCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        WeatheredSentinelsLastTurnAttackersWatcher watcher = game.getState().getWatcher(WeatheredSentinelsLastTurnAttackersWatcher.class);
        if (controller == null || watcher == null) {
            return false;
        }

        for (UUID opponentId : game.getOpponents(controller.getId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null && watcher.checkPlayer(controller.getId(), opponentId)) {
                return true;
            }
        }
        return false;
    }
}

class WeatheredSentinelsLastTurnAttackersWatcher extends Watcher {

    private final Map<UUID, Set<UUID>> playerMap = new HashMap<>();

    WeatheredSentinelsLastTurnAttackersWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case BEGINNING_PHASE_PRE:
                playerMap.remove(game.getActivePlayerId());
                return;
            case ATTACKER_DECLARED:
                UUID attacker = event.getPlayerId();
                Set<UUID> defenders = playerMap.getOrDefault(attacker, new HashSet<>());
                defenders.add(event.getTargetId());
                playerMap.put(attacker, defenders);
        }
    }

    /**
     * Checks if on attackerId's last turn they attacked defenderId.
     *
     * @param attackerId    The ID of the player to see if they attacked the given defender on the attacker's last turn
     * @param defenderId    The ID of the player to see if they were attacked by the attacker on the attacker's last turn
     * @return              Whether the attacker attacked the defender on the attacker's last turn
     */
    boolean checkPlayer(UUID attackerId, UUID defenderId) {
        if (attackerId == null || defenderId == null) {
            return false;
        }
        Set<UUID> defendersLastTurn = playerMap.get(defenderId);
        return defendersLastTurn != null && defendersLastTurn.contains(attackerId);
    }
}
