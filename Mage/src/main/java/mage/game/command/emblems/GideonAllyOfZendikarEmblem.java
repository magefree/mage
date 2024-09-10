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
public final class GideonAllyOfZendikarEmblem extends Emblem {

    public GideonAllyOfZendikarEmblem() {
        super("Emblem Gideon");
        BoostControlledEffect effect = new BoostControlledEffect(1, 1, Duration.EndOfGame);
        Ability ability = new SimpleStaticAbility(Zone.COMMAND, effect);
        this.getAbilities().add(ability);
    }

    private GideonAllyOfZendikarEmblem(final GideonAllyOfZendikarEmblem card) {
        super(card);
    }

    @Override
    public GideonAllyOfZendikarEmblem copy() {
        return new GideonAllyOfZendikarEmblem(this);
    }
}
