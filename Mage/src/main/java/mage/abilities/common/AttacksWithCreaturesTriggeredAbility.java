/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.common;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author Styxo
 */
public class AttacksWithCreaturesTriggeredAbility extends TriggeredAbilityImpl {

    private FilterCreaturePermanent filter;
    private int minAttackers;

    public AttacksWithCreaturesTriggeredAbility(Effect effect, int minAttackers) {
        this(effect, minAttackers, new FilterCreaturePermanent("creatures"));
    }

    public AttacksWithCreaturesTriggeredAbility(Effect effect, int minAttackers, FilterCreaturePermanent filter) {
        super(Zone.BATTLEFIELD, effect);
        this.filter = filter;
        this.minAttackers = minAttackers;
    }

    public AttacksWithCreaturesTriggeredAbility(final AttacksWithCreaturesTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.minAttackers = ability.minAttackers;
    }

    @Override
    public AttacksWithCreaturesTriggeredAbility copy() {
        return new AttacksWithCreaturesTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        int attackerCount = 0;
        for (UUID attacker : game.getCombat().getAttackers()) {
            if (filter.match(game.getPermanent(attacker), game)) {
                attackerCount++;
            }
        }
        return attackerCount >= minAttackers && game.getCombat().getAttackerId().equals(getControllerId());
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder("Whenever you attack with " + minAttackers + " or more ");
        sb.append(filter.getMessage());
        sb.append(", ");
        sb.append(super.getRule());
        return sb.toString();
    }

}
