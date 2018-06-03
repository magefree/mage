

package mage.view;

import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CombatGroupView implements Serializable {
    private static final long serialVersionUID = 1L;

    private final CardsView attackers = new CardsView();
    private final CardsView blockers = new CardsView();
    private String defenderName = "";
    private final UUID defenderId;

    public CombatGroupView(CombatGroup combatGroup, Game game) {
        Player player = game.getPlayer(combatGroup.getDefenderId());
        if (player != null) {
            this.defenderName = player.getName();
        }
        else {
            Permanent perm = game.getPermanent(combatGroup.getDefenderId());
            if (perm != null) {
                this.defenderName = perm.getName();
            }
        }
        this.defenderId = combatGroup.getDefenderId();
        for (UUID id: combatGroup.getAttackers()) {
            Permanent attacker = game.getPermanent(id);
            if (attacker != null) {
                attackers.put(id, new PermanentView(attacker, game.getCard(attacker.getId()),null, game));
            }
        }
        for (UUID id: combatGroup.getBlockerOrder()) {
            Permanent blocker = game.getPermanent(id);
            if (blocker != null) {
                blockers.put(id, new PermanentView(blocker, game.getCard(blocker.getId()), null, game));
            }
        }
    }

    public String getDefenderName() {
        return defenderName;
    }

    public CardsView getAttackers() {
        return attackers;
    }

    public CardsView getBlockers() {
        return blockers;
    }

    public UUID getDefenderId() {
        return defenderId;
    }
}
