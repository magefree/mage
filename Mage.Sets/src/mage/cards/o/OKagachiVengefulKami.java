package mage.cards.o;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public final class OKagachiVengefulKami extends CardImpl {

    public OKagachiVengefulKami(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}{B}{R}{G}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever O-Kagachi, Vengeful Kami deals combat damage to a player, if that player attacked you during their last turn, exile target nonland permanent that player controls
        this.addAbility(new OKagachiVengefulKamiTriggeredAbility());
    }

    private OKagachiVengefulKami(final OKagachiVengefulKami card) {
        super(card);
    }

    @Override
    public OKagachiVengefulKami copy() {
        return new OKagachiVengefulKami(this);
    }
}

class OKagachiVengefulKamiTriggeredAbility extends TriggeredAbilityImpl {

    OKagachiVengefulKamiTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ExileTargetEffect(), false);
        this.addWatcher(new OKagachiVengefulKamiWatcher());
    }

    private OKagachiVengefulKamiTriggeredAbility(final OKagachiVengefulKamiTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public OKagachiVengefulKamiTriggeredAbility copy() {
        return new OKagachiVengefulKamiTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player player = game.getPlayer(event.getTargetId());
        if (player == null
                || !((DamagedPlayerEvent) event).isCombatDamage()
                || !event.getSourceId().equals(getSourceId())) {
            return false;
        }
        this.getEffects().setValue("damagedPlayer", event.getTargetId());
        FilterPermanent filter = new FilterNonlandPermanent("nonland permanent controlled by " + player.getName());
        filter.add(new ControllerIdPredicate(event.getTargetId()));
        this.getTargets().clear();
        this.addTarget(new TargetPermanent(filter));
        return true;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        OKagachiVengefulKamiWatcher watcher = game.getState().getWatcher(OKagachiVengefulKamiWatcher.class);
        if (watcher == null) {
            return false;
        }
        UUID playerId = null;
        for (Effect effect : this.getEffects()) {
            Object obj = effect.getValue("damagedPlayer");
            if (obj instanceof UUID) {
                playerId = (UUID) obj;
                break;
            }
        }
        return watcher.checkPlayer(getControllerId(), playerId);
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, " +
                "if that player attacked you during their last turn, " +
                "exile target nonland permanent that player controls.";
    }
}

class OKagachiVengefulKamiWatcher extends Watcher {

    private final Map<UUID, Set<UUID>> playerMap = new HashMap<>();

    OKagachiVengefulKamiWatcher() {
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
                Set<UUID> defenders = playerMap.get(attacker);
                if (defenders == null) {
                    defenders = new HashSet<>();
                }
                defenders.add(event.getTargetId());
                playerMap.put(attacker, defenders);
        }
    }

    boolean checkPlayer(UUID attackerId, UUID defenderId) {
        if (attackerId != null && defenderId != null) {
            Set<UUID> defendersLastTurn = playerMap.get(defenderId);
            if (defendersLastTurn != null) {
                return defendersLastTurn.contains(attackerId);
            }
        }
        return false;
    }
}
