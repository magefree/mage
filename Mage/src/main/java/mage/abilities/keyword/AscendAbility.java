
package mage.abilities.keyword;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.designations.CitysBlessing;
import mage.designations.DesignationType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.List;

/**
 * @author LevelX2
 */
public class AscendAbility extends SimpleStaticAbility {

    public static final String ASCEND_RULE = "Ascend <i>(If you control ten or more permanents, you get the city's blessing for the rest of the game.)</i>";

    public AscendAbility() {
        super(Zone.BATTLEFIELD, new AscendContinuousEffect());
    }

    protected AscendAbility(final AscendAbility ability) {
        super(ability);
    }

    @Override
    public AscendAbility copy() {
        return new AscendAbility(this);
    }

    public static boolean checkAscend(Game game, Ability source, boolean verbose) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (!controller.hasDesignation(DesignationType.CITYS_BLESSING)) {
                if (game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT, controller.getId(), game) > 9) {
                    return true;
                } else {
                    if (verbose) {
                        game.informPlayers(controller.getLogName() + " does not get the city's blessing.");
                    }
                }
            } else {
                if (verbose) {
                    game.informPlayers(controller.getLogName() + " already has the city's blessing.");
                }
            }
        }
        return false;
    }

    public static void applyAscend(Game game, Player player) {
        player.addDesignation(new CitysBlessing());
        game.informPlayers(player.getLogName() + " gets the city's blessing for the rest of the game.");
    }

    @Override
    public String getRule() {
        return ASCEND_RULE;
    }

}

class AscendContinuousEffect extends ContinuousEffectImpl {

    public AscendContinuousEffect() {
        super(Duration.WhileOnBattlefield, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        staticText = AscendAbility.ASCEND_RULE;
    }

    protected AscendContinuousEffect(final AscendContinuousEffect effect) {
        super(effect);
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            Player player = (Player) object;
            AscendAbility.applyAscend(game, player);
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        if (AscendAbility.checkAscend(game, source, false)) {
            affectedObjects.add(game.getPlayer(source.getControllerId()));
            return true;
        }
        return false;
    }

    @Override
    public AscendContinuousEffect copy() {
        return new AscendContinuousEffect(this);
    }
}
