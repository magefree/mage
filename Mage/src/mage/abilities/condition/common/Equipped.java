package mage.abilities.condition.common;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * Describes condition when creature is equipped.
 *
 * @author nantuko
 */
public class Equipped implements Condition {

    private static Equipped fInstance = new Equipped();

    public static Condition getInstance() {
        return fInstance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getBattlefield().getPermanent(source.getSourceId());
		if (permanent != null) {
			for (UUID uuid : permanent.getAttachments()) {
				Permanent attached = game.getBattlefield().getPermanent(uuid);
				if (attached.getSubtype().contains("Equipment")) {
					return true;
				}
				return false;
			}
		}
		return false;
    }
}
