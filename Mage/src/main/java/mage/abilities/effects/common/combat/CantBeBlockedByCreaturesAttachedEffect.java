package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.EvasionEffect;
import mage.constants.AttachmentType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */
public class CantBeBlockedByCreaturesAttachedEffect extends EvasionEffect {

    private final FilterCreaturePermanent filter;

    public CantBeBlockedByCreaturesAttachedEffect(Duration duration, FilterCreaturePermanent filter, AttachmentType attachmentType) {
        super(duration);
        this.filter = filter;
        this.staticCantBeBlockedMessage =
                new StringBuilder("can't be blocked ")
                        .append(filter.getMessage().startsWith("except by") ? "" : "by ")
                        .append(filter.getMessage())
                        .toString();
        staticText =
                new StringBuilder(attachmentType.verb())
                        .append(" creature ")
                        .append(this.staticCantBeBlockedMessage)
                        .toString();
    }

    protected CantBeBlockedByCreaturesAttachedEffect(final CantBeBlockedByCreaturesAttachedEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getAttachments().contains(source.getSourceId());
    }

    @Override
    public boolean cantBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return filter.match(blocker, source.getControllerId(), source, game);
    }

    @Override
    public CantBeBlockedByCreaturesAttachedEffect copy() {
        return new CantBeBlockedByCreaturesAttachedEffect(this);
    }
}
