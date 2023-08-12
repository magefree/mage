package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.command.Emblem;

/**
 * @author spjspj
 */
public final class ObiWanKenobiEmblem extends Emblem {

    // Creatures you control get +1/+1 and have vigilance, first strike, and lifelink
    public ObiWanKenobiEmblem() {
        super("Emblem Obi-Wan Kenobi");
        Ability ability = new SimpleStaticAbility(Zone.COMMAND, new BoostControlledEffect(1, 1, Duration.EndOfGame));
        Effect effect = new GainAbilityControlledEffect(VigilanceAbility.getInstance(), Duration.EndOfGame);
        effect.setText("and have vigilance");
        ability.addEffect(effect);
        effect = new GainAbilityControlledEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield);
        effect.setText(", first strike");
        ability.addEffect(effect);
        effect = new GainAbilityControlledEffect(LifelinkAbility.getInstance(), Duration.WhileOnBattlefield);
        effect.setText("and lifelink.");
        ability.addEffect(effect);
        getAbilities().add(ability);
    }

    private ObiWanKenobiEmblem(final ObiWanKenobiEmblem card) {
        super(card);
    }

    @Override
    public ObiWanKenobiEmblem copy() {
        return new ObiWanKenobiEmblem(this);
    }
}
