
package mage.abilities.common;

import java.util.UUID;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.effects.common.PassEffect;
import mage.constants.Zone;
import mage.game.Game;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PassAbility extends ActivatedAbilityImpl {

    public PassAbility() {
        super(Zone.ALL, new PassEffect());
        this.usesStack = false;
    }

    public PassAbility(final PassAbility ability) {
        super(ability);
    }

    @Override
    public PassAbility copy() {
        return new PassAbility(this);
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        return ActivationStatus.getTrue();
    }

    @Override
    public String toString() {
        return "Pass";
    }

}
