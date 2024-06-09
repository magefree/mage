package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.AttachmentType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;


/**
 * @author LevelX2
 */
public class CantAttackControllerAttachedEffect extends RestrictionEffect {

    private final boolean orPlaneswalker;

    public CantAttackControllerAttachedEffect(AttachmentType attachmentType, boolean orPlaneswalker) {
        super(Duration.WhileOnBattlefield);
        this.orPlaneswalker = orPlaneswalker;
        this.staticText = attachmentType.verb() + " creature can't attack you" +
                (orPlaneswalker ? " or planeswalkers you control" : "");
    }

    private CantAttackControllerAttachedEffect(final CantAttackControllerAttachedEffect effect) {
        super(effect);
        this.orPlaneswalker = effect.orPlaneswalker;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getAttachments().contains(source.getSourceId());
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        if (source.isControlledBy(defenderId)) {
            return false;
        }
        if (!orPlaneswalker) {
            return true;
        }
        Permanent planeswalker = game.getPermanent(defenderId);
        return planeswalker == null || !planeswalker.isPlaneswalker(game)
                || !planeswalker.isControlledBy(source.getControllerId());
    }

    @Override
    public CantAttackControllerAttachedEffect copy() {
        return new CantAttackControllerAttachedEffect(this);
    }
}
