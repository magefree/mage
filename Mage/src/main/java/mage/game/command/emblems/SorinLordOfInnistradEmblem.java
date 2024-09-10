package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.command.Emblem;

/**
 * @author spjspj
 */
public final class SorinLordOfInnistradEmblem extends Emblem {

    public SorinLordOfInnistradEmblem() {
        super("Emblem Sorin");
        BoostControlledEffect effect = new BoostControlledEffect(1, 0, Duration.EndOfGame);
        Ability ability = new SimpleStaticAbility(Zone.COMMAND, effect);
        this.getAbilities().add(ability);
    }

    private SorinLordOfInnistradEmblem(final SorinLordOfInnistradEmblem card) {
        super(card);
    }

    @Override
    public SorinLordOfInnistradEmblem copy() {
        return new SorinLordOfInnistradEmblem(this);
    }
}
