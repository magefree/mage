package mage.abilities.costs.costadjusters;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.CostAdjuster;
import mage.constants.CommanderCardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public enum CommanderManaValueAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        Player player = game.getPlayer(ability.getControllerId());
        if (player == null) {
            return;
        }
        int maxValue = game
                .getCommanderCardsFromAnyZones(
                        player, CommanderCardType.ANY,
                        Zone.BATTLEFIELD, Zone.COMMAND
                )
                .stream()
                .mapToInt(MageObject::getManaValue)
                .max()
                .orElse(0);
        CardUtil.reduceCost(ability, maxValue);
    }
}
