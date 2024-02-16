package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.command.Emblem;

/**
 * @author TheElk801
 */
public final class GarrukCursedHuntsmanEmblem extends Emblem {

    // -6: You get an emblem with "Creatures you control get +3/+3 and have trample."
    public GarrukCursedHuntsmanEmblem() {
        super("Emblem Garruk");
        Ability ability = new SimpleStaticAbility(
                Zone.COMMAND,
                new BoostControlledEffect(
                        3, 3, Duration.EndOfGame,
                        StaticFilters.FILTER_PERMANENT_CREATURES,
                        false
                ).setText("creatures you control get +3/+3")
        );
        ability.addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(),
                Duration.EndOfGame,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ).setText("and have trample"));
        this.getAbilities().add(ability);
    }

    private GarrukCursedHuntsmanEmblem(final GarrukCursedHuntsmanEmblem card) {
        super(card);
    }

    @Override
    public GarrukCursedHuntsmanEmblem copy() {
        return new GarrukCursedHuntsmanEmblem(this);
    }
}
