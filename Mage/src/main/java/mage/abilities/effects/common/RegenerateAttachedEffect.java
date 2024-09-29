package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.AttachmentType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.Locale;

/**
 * @author jeff
 */
public class RegenerateAttachedEffect extends OneShotEffect {

    protected AttachmentType attachmentType;

    public RegenerateAttachedEffect(AttachmentType attachmentType) {
        super(Outcome.Regenerate);
        this.attachmentType = attachmentType;
        this.setText();
    }

    protected RegenerateAttachedEffect(final RegenerateAttachedEffect effect) {
        super(effect);
        this.attachmentType = effect.attachmentType;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // must use lki cause attachment can be sacrificed to activate regen
        Permanent attachment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (attachment == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(attachment.getAttachedTo());
        if (permanent == null) {
            return false;
        }

        RegenerateTargetEffect regenEffect = new RegenerateTargetEffect();
        regenEffect.setTargetPointer(new FixedTarget(permanent, game));
        game.addEffect(regenEffect, source);
        return true;
    }

    @Override
    public RegenerateAttachedEffect copy() {
        return new RegenerateAttachedEffect(this);
    }

    private void setText() {
        staticText = "Regenerate " + attachmentType.verb().toLowerCase(Locale.ENGLISH) + " creature";
    }
}
