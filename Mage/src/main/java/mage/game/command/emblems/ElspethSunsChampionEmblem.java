package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.command.Emblem;

/**
 * @author spjspj
 */
public final class ElspethSunsChampionEmblem extends Emblem {

    // -7: You get an emblem with "Creatures you control get +2/+2 and have flying."
    public ElspethSunsChampionEmblem() {
        super("Emblem Elspeth");
        Ability ability = new SimpleStaticAbility(
                Zone.COMMAND,
                new BoostControlledEffect(
                        2, 2, Duration.EndOfGame,
                        StaticFilters.FILTER_PERMANENT_CREATURES,
                        false
                ).setText("creatures you control get +2/+2")
        );
        ability.addEffect(new GainAbilityControlledEffect(
                FlyingAbility.getInstance(),
                Duration.EndOfGame,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ).setText("and have flying"));
        this.getAbilities().add(ability);
    }

    private ElspethSunsChampionEmblem(final ElspethSunsChampionEmblem card) {
        super(card);
    }

    @Override
    public ElspethSunsChampionEmblem copy() {
        return new ElspethSunsChampionEmblem(this);
    }
}
