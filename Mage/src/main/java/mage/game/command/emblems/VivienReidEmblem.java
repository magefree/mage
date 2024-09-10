package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.command.Emblem;

/**
 * @author TheElk801
 */
public final class VivienReidEmblem extends Emblem {
    // -8: You get an emblem with "Creatures you control get +2/+2 and have vigilance, trample, and indestructible.

    public VivienReidEmblem() {
        super("Emblem Vivien");
        Ability ability = new SimpleStaticAbility(
                Zone.COMMAND,
                new BoostControlledEffect(
                        2, 2, Duration.EndOfGame,
                        StaticFilters.FILTER_PERMANENT_CREATURES,
                        false
                ).setText("creatures you control get +2/+2")
        );
        ability.addEffect(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(),
                Duration.EndOfGame,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ).setText("and have vigilance"));
        ability.addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(),
                Duration.EndOfGame,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ).setText(", trample"));
        ability.addEffect(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(),
                Duration.EndOfGame,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ).setText(", and indestructible"));
        this.getAbilities().add(ability);
    }

    private VivienReidEmblem(final VivienReidEmblem card) {
        super(card);
    }

    @Override
    public VivienReidEmblem copy() {
        return new VivienReidEmblem(this);
    }
}
