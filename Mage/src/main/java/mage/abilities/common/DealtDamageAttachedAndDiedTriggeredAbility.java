package mage.abilities.common;

import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.AttachmentType;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

public class DealtDamageAttachedAndDiedTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterCreaturePermanent filter;
    private final SetTargetPointer setTargetPointer;

    public DealtDamageAttachedAndDiedTriggeredAbility(Effect effect, boolean optional, FilterCreaturePermanent filter,
                                                      SetTargetPointer setTargetPointer, AttachmentType attachmentType) {
        super(Zone.ALL, effect, optional);
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase(getWhen() + CardUtil.addArticle(filter.getMessage()) + " dealt damage by "
                + CardUtil.getTextWithFirstCharLowerCase(attachmentType.verb()) +
                " creature this turn dies, ");
        setLeavesTheBattlefieldTrigger(true);
    }

    protected DealtDamageAttachedAndDiedTriggeredAbility(final DealtDamageAttachedAndDiedTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public DealtDamageAttachedAndDiedTriggeredAbility copy() {
        return new DealtDamageAttachedAndDiedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent attachment = getSourcePermanentOrLKI(game);
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (attachment == null || !zEvent.isDiesEvent() || !filter.match(zEvent.getTarget(), getControllerId(), this, game)) {
            return false;
        }
        MageObjectReference creatureMOR = new MageObjectReference(attachment.getAttachedTo(), attachment.getAttachedToZoneChangeCounter(), game);
        if (zEvent.getTarget().getDealtDamageByThisTurn()
                .stream()
                .noneMatch(mor -> {
                    if (mor.equals(creatureMOR)) {
                        return true;
                    }
                    Permanent permanent = (Permanent) game.getLastKnownInformation(mor.getSourceId(),
                            Zone.BATTLEFIELD, mor.getZoneChangeCounter());
                    return permanent != null && permanent.getAttachments().contains(getSourceId());
                })) {
            return false;
        }
        switch (setTargetPointer) {
            case PERMANENT:
                getEffects().setTargetPointer(new FixedTarget(creatureMOR));
                break;
            case CARD:
                getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
                break;
            case NONE:
                break;
            default:
                throw new IllegalArgumentException("Unsupported setTargetPointer value in DealtDamageAttachedAndDiedTriggeredAbility");
        }
        return true;
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject sourceObject, GameEvent event) {
        return TriggeredAbilityImpl.isInUseableZoneDiesTrigger(this, sourceObject, event, game);
    }
}
