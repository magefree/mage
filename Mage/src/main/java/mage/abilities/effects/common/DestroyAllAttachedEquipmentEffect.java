package mage.abilities.effects.common;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Quercitron
 */
public class DestroyAllAttachedEquipmentEffect extends OneShotEffect {

    public DestroyAllAttachedEquipmentEffect() {
        super(Outcome.Benefit);
        this.staticText = "Destroy all Equipment attached to that creature";
    }

    public DestroyAllAttachedEquipmentEffect(final DestroyAllAttachedEquipmentEffect effect) {
        super(effect);
    }

    @Override
    public DestroyAllAttachedEquipmentEffect copy() {
        return new DestroyAllAttachedEquipmentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (targetPermanent != null) {
                List<UUID> attachments = new ArrayList<>(targetPermanent.getAttachments());
                for (UUID attachmentId : attachments) {
                    Permanent attachment = game.getPermanent(attachmentId);
                    if (attachment != null && attachment.hasSubtype(SubType.EQUIPMENT, game)) {
                        attachment.destroy(source, game, false);
                    }
                }
            }
            return true;
        }
        return false;
    }

}
