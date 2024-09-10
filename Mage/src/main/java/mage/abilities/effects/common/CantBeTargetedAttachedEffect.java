
package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.AttachmentType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterObject;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;

/**
 * @author LevelX2
 */
public class CantBeTargetedAttachedEffect extends ContinuousRuleModifyingEffectImpl {

    private final FilterObject filterSource;
    private final AttachmentType attachmentType;
    private final TargetController targetController;

    public CantBeTargetedAttachedEffect(FilterObject filterSource, Duration duration, AttachmentType attachmentType, TargetController targetController) {
        super(duration, Outcome.Benefit);
        this.filterSource = filterSource;
        this.attachmentType = attachmentType;
        this.targetController = targetController;
    }

    protected CantBeTargetedAttachedEffect(final CantBeTargetedAttachedEffect effect) {
        super(effect);
        this.filterSource = effect.filterSource.copy();
        this.attachmentType = effect.attachmentType;
        this.targetController = effect.targetController;
    }

    @Override
    public CantBeTargetedAttachedEffect copy() {
        return new CantBeTargetedAttachedEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGET;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent attachment = game.getPermanent(source.getSourceId());
        if (attachment != null && event.getTargetId().equals(attachment.getAttachedTo())) {
            if (targetController == TargetController.OPPONENT
                    && !game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
                return false;
            }
            MageObject mageObject = game.getObject(event.getSourceId());
            MageObject sourceObject;
            if (mageObject instanceof StackAbility) {
                sourceObject = ((StackAbility) mageObject).getSourceObject(game);
            } else {
                sourceObject = mageObject;
            }
            if (mageObject != null && filterSource.match(sourceObject, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(attachmentType.verb()).append(" creature");
        sb.append(" can't be the target of ");
        sb.append(filterSource.getMessage());
        if (!duration.toString().isEmpty()) {
            sb.append(' ');
            if (duration == Duration.EndOfTurn) {
                sb.append("this turn");
            } else {
                sb.append(duration.toString());
            }
        }
        return sb.toString();
    }

}
