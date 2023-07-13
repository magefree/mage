package mage.abilities.effects.common;

import mage.constants.AttachmentType;
import mage.constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX
 */
public class UntapAttachedEffect extends OneShotEffect {

    public UntapAttachedEffect() {
        this(AttachmentType.AURA, "creature");
    }

    public UntapAttachedEffect(AttachmentType attachmentType, String name) {
        super(Outcome.Untap);
        staticText = "untap " + CardUtil.getTextWithFirstCharLowerCase(attachmentType.verb()) + ' ' + name;
    }

    public UntapAttachedEffect(final UntapAttachedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            Permanent attach = game.getPermanent(permanent.getAttachedTo());
            if (attach != null) {
                attach.untap(game);
                return true;
            }
        }
        return false;
    }

    @Override
    public UntapAttachedEffect copy() {
        return new UntapAttachedEffect(this);
    }

}
