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

/**
 * @author spjspj
 */
public final class GarrukApexPredatorEmblem extends Emblem {

    /**
     * Emblem with "Whenever a creature attacks you, it gets +5/+5 and gains
     * trample until end of turn."
     */

    public GarrukApexPredatorEmblem() {
        super("Emblem Garruk");

        Effect effect = new BoostTargetEffect(5, 5, Duration.EndOfTurn);
        effect.setText("it gets +5/+5");
        Ability ability = new AttackedByCreatureTriggeredAbility(Zone.COMMAND, effect, false, SetTargetPointer.PERMANENT);
        effect = new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
        ability.addEffect(effect.setText("and gains trample until end of turn"));
        this.getAbilities().add(ability);
    }

    private GarrukApexPredatorEmblem(final GarrukApexPredatorEmblem card) {
        super(card);
    }

    @Override
    public GarrukApexPredatorEmblem copy() {
        return new GarrukApexPredatorEmblem(this);
    }
}
