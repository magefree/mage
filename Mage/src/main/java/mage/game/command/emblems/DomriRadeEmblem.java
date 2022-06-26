
package mage.game.command.emblems;

import mage.abilities.CompoundAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.command.Emblem;

import java.util.Arrays;

/**
 *
 * @author spjspj
 */
public final class DomriRadeEmblem extends Emblem {
    // "Creatures you control have double strike, trample, hexproof and haste."

    public DomriRadeEmblem() {
        this.setName("Emblem Domri");
        FilterPermanent filter = new FilterControlledCreaturePermanent("Creatures");

        CompoundAbility compoundAbilities = new CompoundAbility(
                DoubleStrikeAbility.getInstance(),
                TrampleAbility.getInstance(),
                HexproofAbility.getInstance(),
                HasteAbility.getInstance()
        );
        this.getAbilities().add(new SimpleStaticAbility(Zone.COMMAND, new GainAbilityControlledEffect(compoundAbilities, Duration.EndOfGame, filter)));

        availableImageSetCodes = Arrays.asList("GTC", "MM3");
    }
}
