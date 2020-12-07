

package mage.player.ai;

import mage.game.permanent.Permanent;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Attackers extends TreeMap<Integer, List<Permanent>> {

    public List<Permanent> getAttackers() {
        List<Permanent> attackers = new ArrayList<>();
        for (List<Permanent> l: this.values()) {
            attackers.addAll(l);
        }
        return attackers;
    }

}
