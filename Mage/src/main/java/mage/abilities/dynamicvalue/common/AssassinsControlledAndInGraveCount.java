package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;

import java.util.Optional;

/**
 * @author TheElk801
 */
public enum AssassinsControlledAndInGraveCount implements DynamicValue {
    FOR_EACH("1", "Assassin you control and each Assassin card in your graveyard"),
    WHERE_X("X", "the number of Assassins you control plus the number of Assassin cards in your graveyard");
    
    private static final FilterPermanent filter1 = new FilterControlledPermanent();
    private static final FilterCard filter2 = new FilterCard();

    static {
        filter2.add(SubType.ASSASSIN.getPredicate());
    }

    private static final Hint hint = new ValueHint("Assassins you control and Assassin cards in your graveyard", FOR_EACH);

    public static Hint getHint() {
        return hint;
    }

    private final String number;
    private final String message;

    AssassinsControlledAndInGraveCount(String number, String message) {
        this.number = number;
        this.message = message;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getBattlefield()
                .count(filter1, sourceAbility.getControllerId(), sourceAbility, game)
                + Optional
                .ofNullable(game.getPlayer(sourceAbility.getControllerId()))
                .map(Player::getGraveyard)
                .map(g -> g.count(filter2, game))
                .orElse(0);
    }

    @Override
    public AssassinsControlledAndInGraveCount copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return number;
    }
}
