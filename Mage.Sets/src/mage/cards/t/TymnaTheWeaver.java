
package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfPostCombatMainTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.watchers.Watcher;

import java.util.*;

/**
 *
 * @author LevelX2
 */
public final class TymnaTheWeaver extends CardImpl {

    public TymnaTheWeaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // At the beginning of your postcombat main phase, you may pay X life, where X is the number of opponents that were dealt combat damage this turn. If you do, draw X cards.
        this.addAbility(new BeginningOfPostCombatMainTriggeredAbility(new TymnaTheWeaverEffect(), TargetController.YOU, true), new TymnaTheWeaverWatcher());

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private TymnaTheWeaver(final TymnaTheWeaver card) {
        super(card);
    }

    @Override
    public TymnaTheWeaver copy() {
        return new TymnaTheWeaver(this);
    }
}

class TymnaTheWeaverEffect extends OneShotEffect {

    public TymnaTheWeaverEffect() {
        super(Outcome.DrawCard);
        this.staticText = "you may pay X life, where X is the number of opponents that were dealt combat damage this turn. If you do, draw X cards";
    }

    public TymnaTheWeaverEffect(final TymnaTheWeaverEffect effect) {
        super(effect);
    }

    @Override
    public TymnaTheWeaverEffect copy() {
        return new TymnaTheWeaverEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            TymnaTheWeaverWatcher watcher = game.getState().getWatcher(TymnaTheWeaverWatcher.class);
            if (watcher != null) {
                int cardsToDraw = watcher.opponentsThatGotCombatDamage(source.getControllerId(), game);
                Cost cost = new PayLifeCost(cardsToDraw);
                if (cost.canPay(source, source, source.getControllerId(), game)
                        && cost.pay(source, game, source, source.getControllerId(), false)) {
                    controller.drawCards(cardsToDraw, source, game);
                }
                return true;
            }
        }
        return false;
    }
}

class TymnaTheWeaverWatcher extends Watcher {

    // private final Set<UUID> players = new HashSet<>();
    private final Map<UUID, Set<UUID>> players = new HashMap<>();

    public TymnaTheWeaverWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER) {
            DamagedPlayerEvent dEvent = (DamagedPlayerEvent) event;
            if (dEvent.isCombatDamage()) {
                if (players.containsKey(event.getTargetId())) { // opponenets can die before number of opponents are checked
                    players.get(event.getTargetId()).addAll(game.getOpponents(event.getTargetId()));
                } else {
                    players.put(event.getTargetId(), game.getOpponents(event.getTargetId()));
                }
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        players.clear();
    }

    public int opponentsThatGotCombatDamage(UUID playerId, Game game) {
        int numberOfOpponents = 0;
        for (Set<UUID> opponents : players.values()) {
            if (opponents.contains(playerId)) {
                numberOfOpponents++;
            }
        }
        return numberOfOpponents;
    }

}
