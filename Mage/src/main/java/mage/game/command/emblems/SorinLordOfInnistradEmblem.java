
package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.command.Emblem;

/**
 *
 * @author spjspj
 */
public final class SorinLordOfInnistradEmblem extends Emblem {

    public SorinLordOfInnistradEmblem() {
        this.setName("Emblem Sorin");
        BoostControlledEffect effect = new BoostControlledEffect(1, 0, Duration.EndOfGame);
        Ability ability = new SimpleStaticAbility(Zone.COMMAND, effect);
        this.getAbilities().add(ability);

        this.setExpansionSetCodeForImage("DKA");
    }
}
