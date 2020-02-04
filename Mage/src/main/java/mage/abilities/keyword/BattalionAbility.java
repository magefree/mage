

package mage.abilities.keyword;

import mage.constants.Zone;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * FAQ 2013/01/11
 *
 * Battalion is an ability word that appears in italics at the beginning of abilities
 * that trigger whenever you attack with that creature and at least two other
 * creatures. (An ability word has no rules meaning.)
 *
 * @author LevelX2
 */


public class BattalionAbility extends TriggeredAbilityImpl {

    public BattalionAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
    }

    public BattalionAbility(final BattalionAbility ability) {
        super(ability);
    }

    @Override
    public BattalionAbility copy() {
        return new BattalionAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getCombat().getAttackers().size() >= 3 && game.getCombat().getAttackers().contains(this.sourceId);
    }

    @Override
    public String getRule() {
        return new StringBuilder("<i>Battalion</i> &mdash; Whenever {this} and at least two other creatures attack, ").append(super.getRule()).toString() ;
    }
}

