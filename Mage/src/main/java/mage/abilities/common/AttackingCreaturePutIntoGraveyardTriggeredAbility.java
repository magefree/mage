/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.common;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import static mage.game.events.GameEvent.EventType.END_COMBAT_STEP_POST;
import static mage.game.events.GameEvent.EventType.REMOVED_FROM_COMBAT;
import static mage.game.events.GameEvent.EventType.ZONE_CHANGE;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author weirddan455 and jeffwadsworth
 */
public class AttackingCreaturePutIntoGraveyardTriggeredAbility extends TriggeredAbilityImpl {

    protected FilterPermanent filterPermanent;
    private final boolean onlyToControllerGraveyard;
    private final boolean itDies;

    public AttackingCreaturePutIntoGraveyardTriggeredAbility(Effect effect, FilterPermanent filterPermanent, Boolean onlyToControllerGraveyard, Boolean itDies, Boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filterPermanent = filterPermanent;
        this.onlyToControllerGraveyard = onlyToControllerGraveyard;
        this.itDies = itDies;
    }

    private AttackingCreaturePutIntoGraveyardTriggeredAbility(final AttackingCreaturePutIntoGraveyardTriggeredAbility ability) {
        super(ability);
        this.filterPermanent = ability.filterPermanent;
        this.onlyToControllerGraveyard = ability.onlyToControllerGraveyard;
        this.itDies = ability.itDies;
    }

    @Override
    public AttackingCreaturePutIntoGraveyardTriggeredAbility copy() {
        return new AttackingCreaturePutIntoGraveyardTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case ATTACKER_DECLARED:
            case END_COMBAT_STEP_POST:
            case ZONE_CHANGE:
            case REMOVED_FROM_COMBAT:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        switch (event.getType()) {
            case ATTACKER_DECLARED:
                Permanent permanent = game.getPermanent(event.getSourceId());
                if (permanent != null
                        && !filterPermanent.match(permanent, game)) {
                    return false;
                }
                List<UUID> attackersList = new ArrayList<>();
                List<UUID> attackersListCopy = (List<UUID>) game.getState().getValue(this.getSourceId() + "Attackers");
                if (attackersListCopy == null) {
                    attackersListCopy = attackersList;
                }
                attackersListCopy.add(event.getSourceId()); // add the filtered creature to the list
                game.getState().setValue(this.getSourceId() + "Attackers", attackersListCopy);
                return false;
            case END_COMBAT_STEP_POST:
                game.getState().setValue(this.getSourceId() + "Attackers", null);
                return false;
            case ZONE_CHANGE:
                ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
                if (zEvent.getFromZone() == Zone.BATTLEFIELD
                        && zEvent.getToZone() == Zone.GRAVEYARD) {
                    if (onlyToControllerGraveyard
                            && !this.isControlledBy(game.getOwnerId(zEvent.getTargetId()))) {
                        return false;
                    }
                    if (itDies
                            && !zEvent.isDiesEvent()) {
                        return false;
                    }
                    List<UUID> attackers = (List<UUID>) game.getState().getValue(this.getSourceId() + "Attackers");
                    return attackers != null
                            && attackers.contains(zEvent.getTargetId());
                }
            case REMOVED_FROM_COMBAT:
                // a card removed from combat is no longer an attacker or blocker so remove it from the list
                List<UUID> attackersListRFC = (List<UUID>) game.getState().getValue(this.getSourceId() + "Attackers");
                if (attackersListRFC != null
                        && attackersListRFC.contains(event.getTargetId())) {
                    attackersListRFC.remove(event.getTargetId());
                    game.getState().setValue(this.getSourceId() + "Attackers", attackersListRFC);
                }

            default:
                return false;
        }
    }

    @Override
    public String getTriggerPhrase() {
        if (itDies) {
            return "Whenever " + filterPermanent.getMessage() + " dies, ";
        }
        return "Whenever " + filterPermanent.getMessage() + " is put into " + (onlyToControllerGraveyard ? "your" : "a")
                + " graveyard from the battlefield, ";
    }

}
