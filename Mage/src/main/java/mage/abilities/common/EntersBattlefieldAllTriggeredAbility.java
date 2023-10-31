package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
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
    protected String rule;
    protected SetTargetPointer setTargetPointer;

    /**
     * zone = BATTLEFIELD optional = false
     */
    public EntersBattlefieldAllTriggeredAbility(Effect effect, FilterPermanent filter) {
        this(Zone.BATTLEFIELD, effect, filter, false);
    }

    public EntersBattlefieldAllTriggeredAbility(Effect effect, FilterPermanent filter, String rule) {
        this(Zone.BATTLEFIELD, effect, filter, false, rule);
    }

    public EntersBattlefieldAllTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean optional) {
        this(zone, effect, filter, optional, SetTargetPointer.NONE, null);
    }

    public EntersBattlefieldAllTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean optional, String rule) {
        this(zone, effect, filter, optional, SetTargetPointer.NONE, rule);
    }

    public EntersBattlefieldAllTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean optional, SetTargetPointer setTargetPointer, String rule) {
        super(zone, effect, optional);
        this.filter = filter;
        this.rule = rule;
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase("Whenever " + getMessageFromFilter() + ", ");
    }

    protected EntersBattlefieldAllTriggeredAbility(final EntersBattlefieldAllTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.rule = ability.rule;
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

//    @Override
//    public String getRule() {
//        if (rule != null && !rule.isEmpty()) {
//            return rule;
//        }
//        return super.getRule();
//    }

    protected String getMessageFromFilter() {
        String filterMessage = filter.getMessage();
        if (filterMessage.startsWith("one or more")) {
            return filterMessage + " enter the battlefield";
        }
        return CardUtil.addArticle(filterMessage) + " enters the battlefield";
    }

    @Override
    public EntersBattlefieldAllTriggeredAbility copy() {
        return new EntersBattlefieldAllTriggeredAbility(this);
    }
}
