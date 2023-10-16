package mage.game.command.planes;

import mage.abilities.Ability;
import mage.abilities.common.ChaosEnsuesTriggeredAbility;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect.HandSizeModification;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.command.Plane;
import mage.players.Player;

/**
 * @author spjspj
 */
public class UndercityReachesPlane extends Plane {

    private static final FilterCard filter = new FilterCard("creature spells");

    static {
        filter.add(CardType.CREATURE.getPredicate());
    }

    public UndercityReachesPlane() {
        this.setPlaneType(Planes.PLANE_UNDERCITY_REACHES);

        // Whenever a creature deals combat damage to a player, its controller may draw a card.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                Zone.COMMAND, new UndercityReachesPlaneEffect(), StaticFilters.FILTER_PERMANENT_A_CREATURE,
                false, SetTargetPointer.PLAYER, true, false
        ));

        // Active player can roll the planar die: Whenever you roll {CHAOS}, you have no maximum hand size for the rest of the game.
        this.addAbility(new ChaosEnsuesTriggeredAbility(new MaximumHandSizeControllerEffect(
                Integer.MAX_VALUE, Duration.EndOfGame, HandSizeModification.SET
        )));
    }

    private UndercityReachesPlane(final UndercityReachesPlane plane) {
        super(plane);
    }

    @Override
    public UndercityReachesPlane copy() {
        return new UndercityReachesPlane(this);
    }
}

class UndercityReachesPlaneEffect extends OneShotEffect {

    UndercityReachesPlaneEffect() {
        super(Outcome.Benefit);
        staticText = "its controller may draw a card";
    }

    private UndercityReachesPlaneEffect(final UndercityReachesPlaneEffect effect) {
        super(effect);
    }

    @Override
    public UndercityReachesPlaneEffect copy() {
        return new UndercityReachesPlaneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        return player != null
                && player.chooseUse(Outcome.DrawCard, "Draw a card?", source, game)
                && player.drawCards(1, source, game) > 0;
    }
}
