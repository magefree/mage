package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.command.Emblem;

/**
 * @author muz
 */
public final class AjaniResoluteEmblem extends Emblem {

    public AjaniResoluteEmblem() {
        super("Emblem Ajani");
        BoostControlledEffect effect = new BoostControlledEffect(2, 2, Duration.EndOfGame);
        Ability ability = new SimpleStaticAbility(Zone.COMMAND, effect);
        this.getAbilities().add(ability);
    }

    private AjaniResoluteEmblem(final AjaniResoluteEmblem card) {
        super(card);
    }

    @Override
    public AjaniResoluteEmblem copy() {
        return new AjaniResoluteEmblem(this);
    }
}
