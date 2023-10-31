package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public class EntersBattlefieldAllTriggeredAbility extends TriggeredAbilityImpl {

    protected FilterPermanent filter;
    protected SetTargetPointer setTargetPointer;

    /**
     * zone = BATTLEFIELD optional = false
     */
    public EntersBattlefieldAllTriggeredAbility(Effect effect, FilterPermanent filter) {
        this(Zone.BATTLEFIELD, effect, filter, false);
    }

    public EntersBattlefieldAllTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean optional) {
        this(zone, effect, filter, optional, SetTargetPointer.NONE);
    }

    public EntersBattlefieldAllTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean optional, SetTargetPointer setTargetPointer) {
        super(zone, effect, optional);
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase(getTriggerPhraseFromFilter() + ", ");
    }

    protected EntersBattlefieldAllTriggeredAbility(final EntersBattlefieldAllTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        UUID targetId = event.getTargetId();
        Permanent permanent = game.getPermanent(targetId);
        if (!filter.match(permanent, getControllerId(), this, game)) {
            return false;
        }
        this.getAllEffects().setValue("permanentEnteringBattlefield", permanent);
        this.getAllEffects().setValue("permanentEnteringControllerId", permanent.getControllerId());
        switch (setTargetPointer) {
            case PLAYER:
                this.getAllEffects().setTargetPointer(new FixedTarget(permanent.getControllerId()));
                break;
            case PERMANENT:
                this.getAllEffects().setTargetPointer(new FixedTarget(permanent, game));
                break;
            default:
        }
        return true;
    }

    protected String getTriggerPhraseFromFilter() {
        String when = (!optional && getAllEffects().stream().anyMatch(
                e -> e instanceof ReturnToHandSourceEffect || e instanceof SacrificeSourceEffect
        ) ? "When " : "Whenever ");
        String filterMessage = filter.getMessage();
        if (filterMessage.startsWith("one or more")) {
            return when + filterMessage + " enter the battlefield";
        }
        return when + CardUtil.addArticle(filterMessage) + " enters the battlefield";
    }

    @Override
    public EntersBattlefieldAllTriggeredAbility copy() {
        return new EntersBattlefieldAllTriggeredAbility(this);
    }
}
