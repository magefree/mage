
package mage.filter.predicate.permanent;

import java.util.UUID;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class EnchantedPredicate implements Predicate<Permanent> {

    @Override
    public boolean apply(Permanent input, Game game) {
        for (UUID attachmentId : input.getAttachments()) {
            Permanent attachment = game.getPermanent(attachmentId);
            if (attachment != null && attachment.isEnchantment()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Enchanted" ;
    }
}
