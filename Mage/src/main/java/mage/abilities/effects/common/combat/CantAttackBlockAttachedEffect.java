package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.AttachmentType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */
public class CantAttackBlockAttachedEffect extends RestrictionEffect {

    public CantAttackBlockAttachedEffect(AttachmentType attachmentType) {
        super(Duration.WhileOnBattlefield, Outcome.Removal);
        this.staticText = attachmentType.verb() + " creature can't attack or block";
    }

    public CantAttackBlockAttachedEffect(final CantAttackBlockAttachedEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getAttachments().contains(source.getSourceId());
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public CantAttackBlockAttachedEffect copy() {
        return new CantAttackBlockAttachedEffect(this);
    }
}
