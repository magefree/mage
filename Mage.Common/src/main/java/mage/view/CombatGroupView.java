

package mage.view;

import mage.MageObjectReference;
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
    private final String defenderName;
    private final UUID defenderId;

    public CombatGroupView(CombatGroup combatGroup, Game game) {
        String defenderName = "";
        UUID defenderId = null;
        Player player = game.getPlayer(combatGroup.getDefenderPlayerID());
        if (player != null) {
            defenderName = player.getName();
            defenderId = player.getId();
        } else {
            MageObjectReference mor = combatGroup.getDefenderMOR();
            Permanent perm = mor == null ? null : mor.getPermanent(game);
            if (perm != null) {
                defenderName = perm.getName();
                defenderId = perm.getId();
            }
        }
        this.defenderName = defenderName;
        this.defenderId = defenderId;

        for (UUID id : combatGroup.getAttackers()) {
            Permanent attacker = game.getPermanent(id);
            if (attacker != null) {
                attackers.put(id, new PermanentView(attacker, game.getCard(attacker.getId()), null, game));
            }
        }
        for (UUID id : combatGroup.getBlockerOrder()) {
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
