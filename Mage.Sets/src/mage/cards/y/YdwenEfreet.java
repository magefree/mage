
package mage.cards.y;

import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BlocksSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.common.BlockedByOnlyOneCreatureThisCombatWatcher;

/**
 *
 * @author MarcoMarin & L_J
 */
public final class YdwenEfreet extends CardImpl {

    public YdwenEfreet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{R}{R}");
        this.subtype.add(SubType.EFREET);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // Whenever Ydwen Efreet blocks, flip a coin. If you lose the flip, remove Ydwen Efreet from combat and it can't block this turn. Creatures it was blocking that had become blocked by only Ydwen Efreet this combat become unblocked.
        this.addAbility(new BlocksSourceTriggeredAbility(new YdwenEfreetEffect(), false));
    }

    private YdwenEfreet(final YdwenEfreet card) {
        super(card);
    }

    @Override
    public YdwenEfreet copy() {
        return new YdwenEfreet(this);
    }
}

class YdwenEfreetEffect extends OneShotEffect {

    public YdwenEfreetEffect() {
        super(Outcome.Damage);
        staticText = "flip a coin. If you lose the flip, remove {this} from combat and it can't block. Creatures it was blocking that had become blocked by only {this} this combat become unblocked";
    }

    private YdwenEfreetEffect(final YdwenEfreetEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent creature = game.getPermanent(source.getSourceId());
        if (controller != null && creature != null) {
            if (!controller.flipCoin(source, game, true)) {
                creature.removeFromCombat(game);
                creature.setMaxBlocks(0);
                
                // Make blocked creatures unblocked
                BlockedByOnlyOneCreatureThisCombatWatcher watcher = game.getState().getWatcher(BlockedByOnlyOneCreatureThisCombatWatcher.class);
                if (watcher != null) {
                    Set<CombatGroup> combatGroups = watcher.getBlockedOnlyByCreature(creature.getId());
                    if (combatGroups != null) {
                        for (CombatGroup combatGroup : combatGroups) {
                            if (combatGroup != null) {
                                combatGroup.setBlocked(false, game);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public YdwenEfreetEffect copy() {
        return new YdwenEfreetEffect(this);
    }
}
