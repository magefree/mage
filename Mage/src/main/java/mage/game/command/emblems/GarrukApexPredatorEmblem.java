package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.AttackedByCreatureTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.game.command.Emblem;

import java.util.Arrays;

/**
 * @author spjspj
 */
public final class GarrukApexPredatorEmblem extends Emblem {

    /**
     * Emblem with "Whenever a creature attacks you, it gets +5/+5 and gains
     * trample until end of turn."
     */

    public GarrukApexPredatorEmblem() {
        setName("Emblem Garruk");

        Effect effect = new BoostTargetEffect(5, 5, Duration.EndOfTurn);
        effect.setText("it gets +5/+5");
        Ability ability = new AttackedByCreatureTriggeredAbility(Zone.COMMAND, effect, false, SetTargetPointer.PERMANENT);
        effect = new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
        ability.addEffect(effect.concatBy("and"));
        this.getAbilities().add(ability);

        availableImageSetCodes = Arrays.asList("M15", "MED");
    }
}
