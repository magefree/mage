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
import mage.filter.FilterPermanent;

/**
 *
 * @author awjackson
 */
public class DestroyAllAttachedToTargetEffect extends OneShotEffect {

    private final FilterPermanent filter;

    public DestroyAllAttachedToTargetEffect(FilterPermanent filter, String description) {
        super(Outcome.DestroyPermanent);
        this.filter = filter;
        this.staticText = "destroy all " + filter.getMessage() + " attached to " + description;
    }

    public DestroyAllAttachedToTargetEffect(final DestroyAllAttachedToTargetEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public DestroyAllAttachedToTargetEffect copy() {
        return new DestroyAllAttachedToTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetPermanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (targetPermanent != null) {
            List<UUID> attachments = new ArrayList<>(targetPermanent.getAttachments());
            for (UUID attachmentId : attachments) {
                Permanent attachment = game.getPermanent(attachmentId);
                if (filter.match(attachment, source.getControllerId(), source, game)) {
                    attachment.destroy(source, game, false);
                }
            }
        }
        return true;
    }
}
