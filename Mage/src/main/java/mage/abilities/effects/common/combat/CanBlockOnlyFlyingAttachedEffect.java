package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.AttachmentType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */

public class CanBlockOnlyFlyingAttachedEffect extends RestrictionEffect {

    public CanBlockOnlyFlyingAttachedEffect(AttachmentType attachmentType) {
        super(Duration.WhileOnBattlefield);
        this.staticText = attachmentType.verb() + " creature can block only creatures with flying";
    }

    protected CanBlockOnlyFlyingAttachedEffect(final CanBlockOnlyFlyingAttachedEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getAttachments().contains(source.getSourceId());
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        if (attacker == null) {
            return true;
        }
        return attacker.hasAbility(FlyingAbility.getInstance(), game);
    }

    @Override
    public CanBlockOnlyFlyingAttachedEffect copy() {
        return new CanBlockOnlyFlyingAttachedEffect(this);
    }

}
