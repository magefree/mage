package mage.game.command.planes;

import mage.abilities.Ability;
import mage.abilities.common.ChaosEnsuesTriggeredAbility;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.constants.Outcome;
import mage.constants.Planes;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.command.Plane;
import mage.players.Player;

/**
 * @author spjspj
 */
public class FieldsOfSummerPlane extends Plane {

    public FieldsOfSummerPlane() {
        this.setPlaneType(Planes.PLANE_FIELDS_OF_SUMMER);

        // Whenever a player casts a spell, that player may gain 2 life
        this.addAbility(new SpellCastAllTriggeredAbility(
                Zone.COMMAND, new FieldsOfSummerEffect(),
                StaticFilters.FILTER_SPELL_A, false, SetTargetPointer.PLAYER
        ));

        // Active player can roll the planar die: Whenever you roll {CHAOS}, you may gain 10 life
        this.addAbility(new ChaosEnsuesTriggeredAbility(new GainLifeEffect(10), true));
    }

    private FieldsOfSummerPlane(final FieldsOfSummerPlane plane) {
        super(plane);
    }

    @Override
    public FieldsOfSummerPlane copy() {
        return new FieldsOfSummerPlane(this);
    }
}

class FieldsOfSummerEffect extends OneShotEffect {

    public FieldsOfSummerEffect() {
        super(Outcome.GainLife);
        this.staticText = "that player may gain 2 life";
    }

    protected FieldsOfSummerEffect(final FieldsOfSummerEffect effect) {
        super(effect);
    }

    @Override
    public FieldsOfSummerEffect copy() {
        return new FieldsOfSummerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        return player != null
                && player.chooseUse(Outcome.Benefit, "Gain 2 life?", source, game)
                && player.gainLife(2, game, source) > 0;
    }
}
