package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.hint.Hint;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;

/**
 * @author TheElk801
 */
public enum DesertControlledOrGraveyardCondition implements Condition {
    instance;

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.DESERT);
    private static final FilterCard filter2 = new FilterCard();

    static {
        filter2.add(SubType.DESERT.getPredicate());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (game.getBattlefield().contains(filter, source, game, 1)) {
            return true;
        }
        Player player = game.getPlayer(source.getControllerId());
        return player != null && player.getGraveyard().count(filter2, game) > 0;
    }

    @Override
    public String toString() {
        return "you control a Desert or there is a Desert card in your graveyard";
    }

    public static Hint getHint() {
        return DesertControlledOrGraveyardHint.instance;
    }

    enum DesertControlledOrGraveyardHint implements Hint {
        instance;

        @Override
        public String getText(Game game, Ability ability) {
            StringBuilder sb = new StringBuilder();
            if (game.getBattlefield().contains(filter, ability, game, 1)) {
                sb.append("You control a Desert.");
            }
            Player player = game.getPlayer(ability.getControllerId());
            if (player != null && player.getGraveyard().count(filter2, game) > 0) {
                if (sb.length() > 0) {
                    sb.append("<br>");
                }
                sb.append("You have a Desert card in your graveyard.");
            }
            return sb.toString();
        }

        @Override
        public Hint copy() {
            return this;
        }
    }
}
