package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.command.Emblem;
import mage.target.common.TargetAnyTarget;

/**
 * @author spjspj
 */
public final class ArlinnEmbracedByTheMoonEmblem extends Emblem {
    // "Creatures you control have haste and '{T}: This creature deals damage equal to its power to any target.'"

    public ArlinnEmbracedByTheMoonEmblem() {
        super("Emblem Arlinn");
        FilterPermanent filter = new FilterControlledCreaturePermanent("Creatures");
        GainAbilityControlledEffect effect = new GainAbilityControlledEffect(HasteAbility.getInstance(), Duration.EndOfGame, filter);
        effect.setText("Creatures you control have haste");
        Ability ability = new SimpleStaticAbility(Zone.COMMAND, effect);
        Effect effect2 = new DamageTargetEffect(new SourcePermanentPowerCount());
        effect2.setText("This creature deals damage equal to its power to any target");
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect2, new TapSourceCost());
        ability2.addTarget(new TargetAnyTarget());
        effect = new GainAbilityControlledEffect(ability2, Duration.EndOfGame, filter);
        effect.setText("and '{T}: This creature deals damage equal to its power to any target");
        ability.addEffect(effect);
        this.getAbilities().add(ability);
    }

    private ArlinnEmbracedByTheMoonEmblem(final ArlinnEmbracedByTheMoonEmblem card) {
        super(card);
    }

    @Override
    public ArlinnEmbracedByTheMoonEmblem copy() {
        return new ArlinnEmbracedByTheMoonEmblem(this);
    }
}
