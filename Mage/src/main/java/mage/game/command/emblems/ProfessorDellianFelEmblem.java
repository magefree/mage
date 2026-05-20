package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedGainedLifeValue;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.constants.Zone;
import mage.game.command.Emblem;
import mage.target.common.TargetOpponent;

/**
 * @author TheElk801
 */
public final class ProfessorDellianFelEmblem extends Emblem {

    // -6: You get an emblem with "Whenever you gain life, target opponent loses that much life."
    public ProfessorDellianFelEmblem() {
        super("Emblem Dellian");
        Ability ability = new GainLifeControllerTriggeredAbility(
                Zone.COMMAND, new LoseLifeTargetEffect(SavedGainedLifeValue.MUCH), false, false
        );
        ability.addTarget(new TargetOpponent());
        this.getAbilities().add(ability);
    }

    private ProfessorDellianFelEmblem(final ProfessorDellianFelEmblem card) {
        super(card);
    }

    @Override
    public ProfessorDellianFelEmblem copy() {
        return new ProfessorDellianFelEmblem(this);
    }
}
