
package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.command.Emblem;

/**
 *
 * @author spjspj
 */
public class ElspethSunsChampionEmblem extends Emblem {
    // -7: You get an emblem with "Creatures you control get +2/+2 and have flying."

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures");

    public ElspethSunsChampionEmblem() {
        this.setName("Emblem Elspeth");
        this.setExpansionSetCodeForImage("THS");
        Ability ability = new SimpleStaticAbility(Zone.COMMAND, new BoostControlledEffect(2, 2, Duration.EndOfGame, filter, false));
        ability.addEffect(new GainAbilityControlledEffect(FlyingAbility.getInstance(), Duration.EndOfGame, filter));
        this.getAbilities().add(ability);

    }
}
