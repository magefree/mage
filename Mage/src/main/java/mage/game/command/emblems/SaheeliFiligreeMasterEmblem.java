package mage.game.command.emblems;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.command.Emblem;

/**
 * @author PurpleCrowbar
 */
public final class SaheeliFiligreeMasterEmblem extends Emblem {

    // −4: You get an emblem with "Artifact creatures you control get +1/+1" and "Artifact spells you cast cost {1} less to cast."
    public SaheeliFiligreeMasterEmblem() {
        this.setName("Emblem Saheeli");
        this.setExpansionSetCodeForImage("BRO");
        this.getAbilities().add(new SimpleStaticAbility(
                Zone.COMMAND,
                new BoostControlledEffect(
                        1, 1, Duration.EndOfGame,
                        StaticFilters.FILTER_PERMANENT_ARTIFACT_CREATURE
                ).setText("Artifact creatures you control get +1/+1")
        ));
        this.getAbilities().add(new SimpleStaticAbility(
                Zone.COMMAND,
                new SpellsCostReductionControllerEffect(
                        StaticFilters.FILTER_CARD_ARTIFACT, 1
                ).setText("Artifact spells you cast cost {1} less to cast")
        ));
    }
}
