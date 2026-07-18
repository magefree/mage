
package mage.abilities.keyword;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.designations.EnduringStory;
import mage.designations.DesignationType;
import mage.game.Game;
import mage.players.Player;

/**
 * @author muz
 */
public class StoriedAbility extends SimpleStaticAbility {

    public static final String STORIED_RULE = "Storied <i>(If you control three or more artifacts, legendaries, and/or Sagas, you have an enduring story for the rest of the game.)</i>";
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("artifacts, legendaries, and/or Sagas");

    static {
        filter.add(Predicates.or(
            CardType.ARTIFACT.getPredicate(),
            SuperType.LEGENDARY.getPredicate(),
            SubType.SAGA.getPredicate()
        ));
    }

    public StoriedAbility() {
        super(Zone.BATTLEFIELD, new StoriedContinuousEffect());
    }

    protected StoriedAbility(final StoriedAbility ability) {
        super(ability);
    }

    @Override
    public StoriedAbility copy() {
        return new StoriedAbility(this);
    }

    public static boolean checkStoried(Game game, Ability source, boolean verbose) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (!controller.hasDesignation(DesignationType.ENDURING_STORY)) {
                if (game.getBattlefield().countAll(filter, controller.getId(), game) >= 3) {
                    controller.addDesignation(new EnduringStory());
                    game.informPlayers(controller.getLogName() + " has an enduring story for the rest of the game.");
                } else {
                    if (verbose) {
                        game.informPlayers(controller.getLogName() + " does not have an enduring story.");
                    }
                }
            } else {
                if (verbose) {
                    game.informPlayers(controller.getLogName() + " already has an enduring story.");
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return STORIED_RULE;
    }

}

class StoriedContinuousEffect extends ContinuousEffectImpl {

    public StoriedContinuousEffect() {
        super(Duration.WhileOnBattlefield, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        staticText = StoriedAbility.STORIED_RULE;
    }

    protected StoriedContinuousEffect(final StoriedContinuousEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return StoriedAbility.checkStoried(game, source, false);
    }

    @Override
    public StoriedContinuousEffect copy() {
        return new StoriedContinuousEffect(this);
    }
}
