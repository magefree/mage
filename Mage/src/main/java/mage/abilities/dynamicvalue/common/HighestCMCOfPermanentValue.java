package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class HighestCMCOfPermanentValue implements DynamicValue {

    private final FilterPermanent filter;
    private final boolean onlyIfCanBeSacrificed;

    public HighestCMCOfPermanentValue(FilterPermanent filter, boolean onlyIfCanBeSacrificed) {
        super();
        this.filter = filter;
        this.onlyIfCanBeSacrificed = onlyIfCanBeSacrificed;
    }

    public HighestCMCOfPermanentValue(final HighestCMCOfPermanentValue dynamicValue) {
        this.filter = dynamicValue.filter;
        this.onlyIfCanBeSacrificed = dynamicValue.onlyIfCanBeSacrificed;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int value = 0;
        Player controller = game.getPlayer(sourceAbility.getControllerId());
        if (controller != null) {
            for (Permanent permanent : game.getBattlefield()
                    .getActivePermanents(filter, sourceAbility.getControllerId(), sourceAbility, game)) {
                if ((!onlyIfCanBeSacrificed || controller.canPaySacrificeCost(permanent, sourceAbility, sourceAbility.getControllerId(), game))
                        && permanent.getManaValue() > value) {
                    value = permanent.getManaValue();
                }

            }
        }
        return value;
    }

    @Override
    public HighestCMCOfPermanentValue copy() {
        return new HighestCMCOfPermanentValue(this);
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return filter.getMessage();
    }
}
