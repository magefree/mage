
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LoneFox
 */
public class DestroyAttachedToEffect extends OneShotEffect {

    private final boolean noRegen;

    public DestroyAttachedToEffect(String description) {
        this(description, false);
    }

    public DestroyAttachedToEffect(String description, boolean noRegen) {
        super(Outcome.DestroyPermanent);
        this.noRegen = noRegen;
        this.staticText = "destroy " + description;
        if (noRegen) {
            this.staticText += ". It can't be regenerated";
        }
    }

    protected DestroyAttachedToEffect(final DestroyAttachedToEffect effect) {
        super(effect);
        this.noRegen = effect.noRegen;
    }

    @Override
    public DestroyAttachedToEffect copy() {
        return new DestroyAttachedToEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent attachment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (attachment != null && attachment.getAttachedTo() != null) {
            Permanent attachedTo = game.getPermanent(attachment.getAttachedTo());
            if (attachedTo != null) {
                return attachedTo.destroy(source, game, noRegen);
            }
        }
        return false;
    }
}
