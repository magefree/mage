
package mage.cards.t;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttacksIfAbleTargetPlayerSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.RandomUtil;
import mage.watchers.Watcher;

/**
 *
 * @author TheElk801
 */
public final class TerritorialHellkite extends CardImpl {

    public TerritorialHellkite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // At the beginning of combat on your turn, choose an opponent at random that Territorial Hellkite didn't attack during your last combat. Territorial Hellkite attacks that player this combat if able. If you can't choose an opponent this way, tap Territorial Hellkite.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new AttackIfAbleTargetRandoOpponentSourceEffect(), TargetController.YOU, false), new AttackedLastCombatWatcher());
    }

    private TerritorialHellkite(final TerritorialHellkite card) {
        super(card);
    }

    @Override
    public TerritorialHellkite copy() {
        return new TerritorialHellkite(this);
    }
}

class AttackedLastCombatWatcher extends Watcher {

    // Map<lastCombatOfPlayerId, Map<attackingCreature, attackedPlayerId>>
    private final Map<UUID, Map<MageObjectReference, UUID>> attackedLastCombatPlayers = new HashMap<>();

    public AttackedLastCombatWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DECLARE_ATTACKERS_STEP_PRE) {
            // Remove previous attacking creatures of the current combat's player if info exists
            attackedLastCombatPlayers.remove(game.getCombat().getAttackingPlayerId());
        }
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED) {
            // remember which attacker attacked which player
            Map<MageObjectReference, UUID> attackedPlayers = new HashMap<>();
            for (UUID attackerId : game.getCombat().getAttackers()) {
                Permanent attacker = game.getPermanent(attackerId);
                if (attacker != null) {
                    attackedPlayers.put(new MageObjectReference(attacker, game), game.getCombat().getDefenderId(attackerId));
                }
            }
            attackedLastCombatPlayers.put(game.getCombat().getAttackingPlayerId(), attackedPlayers);
        }
    }

    public Map<MageObjectReference, UUID> getAttackedLastCombatPlayers(UUID combatPlayerId) {
        return attackedLastCombatPlayers.get(combatPlayerId);
    }
}

class AttackIfAbleTargetRandoOpponentSourceEffect extends OneShotEffect {

    public AttackIfAbleTargetRandoOpponentSourceEffect() {
        super(Outcome.Benefit);
        this.staticText = "choose an opponent at random that {this} didn't attack during your last combat. {this} attacks that player this combat if able. If you can't choose an opponent this way, tap {this}";
    }

    private AttackIfAbleTargetRandoOpponentSourceEffect(final AttackIfAbleTargetRandoOpponentSourceEffect effect) {
        super(effect);
    }

    @Override
    public AttackIfAbleTargetRandoOpponentSourceEffect copy() {
        return new AttackIfAbleTargetRandoOpponentSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        AttackedLastCombatWatcher watcher = game.getState().getWatcher(AttackedLastCombatWatcher.class);
        if (controller != null && sourcePermanent != null && watcher != null) {
            List<UUID> opponents = new ArrayList<>();
            Map<MageObjectReference, UUID> attackedPlayers = watcher.getAttackedLastCombatPlayers(source.getControllerId());
            MageObjectReference mor = new MageObjectReference(sourcePermanent, game);
            if (attackedPlayers == null) {
                opponents.addAll(game.getOpponents(controller.getId()));
            } else {
                for (UUID opp : game.getOpponents(controller.getId())) {
                    if (!opp.equals(attackedPlayers.getOrDefault(mor, null))) {
                        opponents.add(opp);
                    }
                }
            }
            if (!opponents.isEmpty()) {
                Player opponent = game.getPlayer(opponents.get(RandomUtil.nextInt(opponents.size())));
                if (opponent != null) {
                    ContinuousEffect effect = new AttacksIfAbleTargetPlayerSourceEffect();
                    effect.setTargetPointer(new FixedTarget(opponent.getId()));
                    game.addEffect(effect, source);
                    game.informPlayers(sourcePermanent.getLogName() + " has to attack " + opponent.getLogName() + ".");
                }
            } else {
                game.informPlayers(sourcePermanent.getLogName() + " can't attack an opponent it didn't attack last combat.");
                sourcePermanent.tap(source, game);
            }
            return true;
        }

        return false;
    }
}
