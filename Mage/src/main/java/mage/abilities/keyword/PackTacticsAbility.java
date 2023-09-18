package mage.abilities.keyword;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.AbilityWord;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class PackTacticsAbility extends TriggeredAbilityImpl {

    public PackTacticsAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
        this.replaceRuleText = false;
        this.setAbilityWord(AbilityWord.PACK_TACTICS);
        setTriggerPhrase("Whenever {this} attacks, if you attacked with creatures with total power 6 or greater this combat, ");
    }

    private PackTacticsAbility(final PackTacticsAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (getSourceId().equals(event.getSourceId())) {
            int power = 0;
            for (UUID attackerId : game.getCombat().getAttackers()) {
                Permanent attacker = game.getPermanent(attackerId);
                if (attacker != null) {
                    power += attacker.getPower().getValue();
                }
            }
            return power >= 6;
        }
        return false;
    }

    @Override
    public PackTacticsAbility copy() {
        return new PackTacticsAbility(this);
    }
}
