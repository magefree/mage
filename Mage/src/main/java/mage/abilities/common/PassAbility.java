package mage.abilities.common;

import mage.ApprovingObject;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.effects.common.PassEffect;
import mage.constants.Zone;
import mage.game.Game;

import java.util.UUID;

/**
 * AI: fake ability to pass priority in game simulations
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PassAbility extends ActivatedAbilityImpl {

    public PassAbility() {
        super(Zone.ALL, new PassEffect(), null);
        this.usesStack = false;
    }

    protected PassAbility(final PassAbility ability) {
        super(ability);
    }

    @Override
    public PassAbility copy() {
        return new PassAbility(this);
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        return new ActivationStatus(new ApprovingObject(this, game));
    }

    @Override
    public String toString() {
        return "Pass";
    }

}
