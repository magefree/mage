
package mage.cards.t;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class TideOfWar extends CardImpl {

    public TideOfWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{R}{R}");


        // Whenever one or more creatures block, flip a coin. If you win the flip, each blocking creature is sacrificed by its controller. If you lose the flip, each blocked creature is sacrificed by its controller.
        this.addAbility(new BlocksTriggeredAbility(new TideOfWarEffect(), false));
    }

    public TideOfWar(final TideOfWar card) {
        super(card);
    }

    @Override
    public TideOfWar copy() {
        return new TideOfWar(this);
    }
}

class BlocksTriggeredAbility extends TriggeredAbilityImpl {

    public BlocksTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public BlocksTriggeredAbility(final BlocksTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DECLARED_BLOCKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        for (CombatGroup combatGroup: game.getCombat().getGroups()) {
            if (!combatGroup.getBlockers().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more creatures block, " + super.getRule();
    }

    @Override
    public BlocksTriggeredAbility copy() {
        return new BlocksTriggeredAbility(this);
    }
}

class TideOfWarEffect extends OneShotEffect {

    public TideOfWarEffect() {
        super(Outcome.Benefit);
        this.staticText = "flip a coin. If you win the flip, each blocking creature is sacrificed by its controller. If you lose the flip, each blocked creature is sacrificed by its controller";
    }

    public TideOfWarEffect(final TideOfWarEffect effect) {
        super(effect);
    }

    @Override
    public TideOfWarEffect copy() {
        return new TideOfWarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<UUID> toSacrifice = new HashSet<>();
            if (controller.flipCoin(game)) {
                // each blocking creature is sacrificed by its controller
                for (CombatGroup combatGroup: game.getCombat().getGroups()) {
                    for (UUID blockerId: combatGroup.getBlockers()) {
                        toSacrifice.add(blockerId);
                    }
                }
            } else {
                // each blocked creature is sacrificed by its controller
                for (CombatGroup combatGroup: game.getCombat().getGroups()) {
                    if (!combatGroup.getBlockers().isEmpty()) {
                        for (UUID attackerId: combatGroup.getAttackers()) {
                            toSacrifice.add(attackerId);
                        }
                    }
                }
            }
            for(UUID creatureId: toSacrifice) {
                Permanent creature = game.getPermanent(creatureId);
                if (creature != null) {
                    creature.sacrifice(source.getSourceId(), game);
                    Player player = game.getPlayer(creature.getControllerId());
                    if (player != null) {
                        game.informPlayers(new StringBuilder(player.getLogName()).append(" sacrifices ").append(creature.getName()).toString());
                    }
                }
            }
            return true;
        }
        return false;
    }
}
