package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public class AttacksAllTriggeredAbility extends TriggeredAbilityImpl {

    protected FilterCreaturePermanent filter;
    protected boolean attacksYouOrYourPlaneswalker;
    protected SetTargetPointer setTargetPointer;
    protected boolean controller;

    public AttacksAllTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, StaticFilters.FILTER_PERMANENT_CREATURE, SetTargetPointer.NONE, false);
    }

    public AttacksAllTriggeredAbility(Effect effect, boolean optional, boolean attacksYouOrYourPlaneswalker) {
        this(effect, optional, StaticFilters.FILTER_PERMANENT_CREATURE, SetTargetPointer.NONE, attacksYouOrYourPlaneswalker);
    }

    public AttacksAllTriggeredAbility(Effect effect, boolean optional, FilterCreaturePermanent filter, SetTargetPointer setTargetPointer, boolean attacksYouOrYourPlaneswalker) {
        this(effect, optional, filter, setTargetPointer, attacksYouOrYourPlaneswalker, false);
    }

    public AttacksAllTriggeredAbility(Effect effect, boolean optional, FilterCreaturePermanent filter, SetTargetPointer setTargetPointer, boolean attacksYouOrYourPlaneswalker, boolean controller) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
        this.attacksYouOrYourPlaneswalker = attacksYouOrYourPlaneswalker;
        this.setTargetPointer = setTargetPointer;
        this.controller = controller;
    }

    public AttacksAllTriggeredAbility(final AttacksAllTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter.copy();
        this.attacksYouOrYourPlaneswalker = ability.attacksYouOrYourPlaneswalker;
        this.setTargetPointer = ability.setTargetPointer;
        this.controller = ability.controller;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (filter.match(permanent, getSourceId(), getControllerId(), game)) {
            if (attacksYouOrYourPlaneswalker) {
                boolean check = false;
                if (event.getTargetId().equals(getControllerId())) {
                    check = true;
                } else {
                    Permanent planeswalker = game.getPermanent(event.getTargetId());
                    if (planeswalker != null && planeswalker.isPlaneswalker(game) && planeswalker.isControlledBy(getControllerId())) {
                        check = true;
                    }
                }
                if (!check) {
                    return false;
                }
            }
            getEffects().setValue("attacker", permanent);
            switch (setTargetPointer) {
                case PERMANENT:
                    getEffects().setTargetPointer(new FixedTarget(permanent, game));
                    break;
                case PLAYER:
                    UUID playerId = controller ? permanent.getControllerId() : game.getCombat().getDefendingPlayerId(permanent.getId(), game);
                    if (playerId != null) {
                        getEffects().setTargetPointer(new FixedTarget(playerId));
                    }
                    break;
            }
            return true;
        }
        return false;
    }

    @Override
    public AttacksAllTriggeredAbility copy() {
        return new AttacksAllTriggeredAbility(this);
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever " + CardUtil.addArticle(filter.getMessage()) + " attacks"
                + (attacksYouOrYourPlaneswalker ? " you or a planeswalker you control" : "") + ", ";
    }

}
