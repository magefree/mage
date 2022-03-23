package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.AttachmentType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import static mage.constants.Duration.EndOfTurn;

/**
 * @author North
 */
public class CantBlockAttachedEffect extends RestrictionEffect {

    private final FilterCreaturePermanent filter;

    public CantBlockAttachedEffect(AttachmentType attachmentType) {
        this(attachmentType, Duration.WhileOnBattlefield);
    }

    public CantBlockAttachedEffect(AttachmentType attachmentType, Duration duration) {
        this(attachmentType, duration, new FilterCreaturePermanent());
    }

    public CantBlockAttachedEffect(AttachmentType attachmentType, Duration duration, FilterCreaturePermanent filter) {
        super(duration);
        this.filter = filter;
        StringBuilder sb = new StringBuilder();
        sb.append(attachmentType.verb());
        sb.append(" creature can't block");
        if (!filter.getMessage().equals("creature")) {
            sb.append(' ').append(filter.getMessage());
        }
        if (duration == EndOfTurn) {
            sb.append(" this turn");
        } else if (!duration.toString().isEmpty()) {
            sb.append(' ').append(duration.toString());
        }
        staticText = sb.toString();
    }

    public CantBlockAttachedEffect(final CantBlockAttachedEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (affectedObjectsSet) {
            Permanent equipment = game.getPermanent(source.getSourceId());
            if (equipment != null && equipment.getAttachedTo() != null) {
                this.setTargetPointer(new FixedTarget(equipment.getAttachedTo(), game.getState().getZoneChangeCounter(equipment.getAttachedTo())));
            }
        }
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (affectedObjectsSet) {
            return targetPointer.getFirst(game, source).equals(permanent.getId());
        }
        return permanent.getAttachments().contains(source.getSourceId());
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        if (attacker == null) {
            return true;
        }
        return !filter.match(attacker, source.getControllerId(), source, game);
    }

    @Override
    public CantBlockAttachedEffect copy() {
        return new CantBlockAttachedEffect(this);
    }
}
