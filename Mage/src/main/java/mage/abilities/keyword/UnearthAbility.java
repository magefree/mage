package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;

/**
 * @author BetaSteward_at_googlemail.com
 * <p>
 * <p>
 * 702.82. Unearth
 * <p>
 * 702.82a Unearth is an activated ability that functions while the card with
 * unearth is in a graveyard. "Unearth [cost]" means "[Cost]: Return this card
 * from your graveyard to the battlefield. It gains haste. Exile it at the
 * beginning of the next end step. If it would leave the battlefield, exile it
 * instead of putting it anywhere else. Activate this ability only any time you
 * could cast a sorcery."
 */
public class UnearthAbility extends ActivatedAbilityImpl {

    public UnearthAbility(ManaCosts costs) {
        super(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToBattlefieldEffect(false, true, true), costs);
        this.timing = TimingRule.SORCERY;
        this.addEffect(new CreateDelayedTriggeredAbilityEffect(new UnearthDelayedTriggeredAbility()));
        this.addEffect(new UnearthLeavesBattlefieldEffect());
    }

    protected UnearthAbility(final UnearthAbility ability) {
        super(ability);
    }

    @Override
    public UnearthAbility copy() {
        return new UnearthAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder("Unearth ").append(this.getManaCosts().getText());
        sb.append(" <i>(").append(this.getManaCosts().getText());
        sb.append(": Return this card from your graveyard to the battlefield. It gains haste. Exile it at the beginning of the next end step or if it would leave the battlefield. Unearth only as a sorcery.)</i>");
        return sb.toString();
    }

}

class UnearthDelayedTriggeredAbility extends DelayedTriggeredAbility {

    public UnearthDelayedTriggeredAbility() {
        super(new ExileSourceEffect());
    }

    protected UnearthDelayedTriggeredAbility(final UnearthDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public UnearthDelayedTriggeredAbility copy() {
        return new UnearthDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.END_TURN_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.controllerId);
    }

    @Override
    public String getRule() {
        return "Exile {this} at the beginning of the next end step";
    }

}

class UnearthLeavesBattlefieldEffect extends ReplacementEffectImpl {

    public UnearthLeavesBattlefieldEffect() {
        super(Duration.OneUse, Outcome.Exile);
        staticText = "When {this} leaves the battlefield, exile it";
    }

    protected UnearthLeavesBattlefieldEffect(final UnearthLeavesBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public UnearthLeavesBattlefieldEffect copy() {
        return new UnearthLeavesBattlefieldEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getSourceId())) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getFromZone() == Zone.BATTLEFIELD && zEvent.getToZone() != Zone.EXILED) {
                // Only move it to exile if it was this instance that was moved to battlefield with unearth
                return source.getSourceObjectZoneChangeCounter() == game.getState().getZoneChangeCounter(source.getSourceId());
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((ZoneChangeEvent) event).setToZone(Zone.EXILED);
        return false;
    }
}
