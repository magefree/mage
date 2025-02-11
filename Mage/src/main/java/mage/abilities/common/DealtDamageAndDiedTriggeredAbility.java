package mage.abilities.common;

import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

public class DealtDamageAndDiedTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterCreaturePermanent filter;
    protected SetTargetPointer setTargetPointer;

    public DealtDamageAndDiedTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public DealtDamageAndDiedTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, StaticFilters.FILTER_PERMANENT_CREATURE);
    }

    public DealtDamageAndDiedTriggeredAbility(Effect effect, boolean optional, FilterCreaturePermanent filter) {
        this(effect, optional, filter, SetTargetPointer.PERMANENT);
    }

    public DealtDamageAndDiedTriggeredAbility(Effect effect, boolean optional, FilterCreaturePermanent filter, SetTargetPointer setTargetPointer) {
        super(Zone.ALL, effect, optional);
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase(getWhen() + CardUtil.addArticle(filter.getMessage()) + " dealt damage by {this} this turn dies, ");
        setLeavesTheBattlefieldTrigger(true);
    }

    protected DealtDamageAndDiedTriggeredAbility(final DealtDamageAndDiedTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public DealtDamageAndDiedTriggeredAbility copy() {
        return new DealtDamageAndDiedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // If Axelrod Gunnarson and a creature it dealt damage to are both put into a graveyard at the same time,
        // Axelrod Gunnarson’s second ability will trigger.
        // (2009-10-01)
        if (((ZoneChangeEvent) event).isDiesEvent()) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (filter.match(zEvent.getTarget(), game)) {
                boolean damageDealt = false;
                for (MageObjectReference mor : zEvent.getTarget().getDealtDamageByThisTurn()) {
                    if (mor.refersTo(game.getLastKnownInformation(getSourceId(), Zone.BATTLEFIELD), game)
                            || (mor.refersTo(getSourceObject(game), game))) {
                        damageDealt = true;
                        break;
                    }
                }
                if (damageDealt) {
                    if (this.setTargetPointer == SetTargetPointer.PERMANENT) {
                        for (Effect effect : getEffects()) {
                            effect.setTargetPointer(new FixedTarget(event.getTargetId(), game));
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject sourceObject, GameEvent event) {
        return TriggeredAbilityImpl.isInUseableZoneDiesTrigger(this, sourceObject, event, game);
    }
}
