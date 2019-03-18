
package mage.game.command.emblems;

import mage.abilities.Ability;
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

/**
 *
 * @author spjspj
 */
public final class DomriRadeEmblem extends Emblem {
    // "Creatures you control have double strike, trample, hexproof and haste."

    public DomriRadeEmblem() {
        this.setName("Emblem Domri");
        FilterPermanent filter = new FilterControlledCreaturePermanent("Creatures");
        GainAbilityControlledEffect effect = new GainAbilityControlledEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfGame, filter);
        Ability ability = new SimpleStaticAbility(Zone.COMMAND, effect);
        effect = new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.EndOfGame, filter);
        ability.addEffect(effect);
        effect = new GainAbilityControlledEffect(HexproofAbility.getInstance(), Duration.EndOfGame, filter);
        ability.addEffect(effect);
        effect = new GainAbilityControlledEffect(HasteAbility.getInstance(), Duration.EndOfGame, filter);
        ability.addEffect(effect);
        this.getAbilities().add(ability);
    }
}
