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
public enum CavesControlledAndInGraveCount implements DynamicValue {
    FOR_EACH("1", "Cave you control and each Cave card in your graveyard"),
    WHERE_X("X", "the number of Caves you control plus the number of Cave cards in your graveyard");
    private static final FilterPermanent filter1 = new FilterControlledPermanent(SubType.CAVE);
    private static final FilterCard filter2 = new FilterCard();

    static {
        filter2.add(SubType.CAVE.getPredicate());
    }

    private static final Hint hint = new ValueHint("Caves you control and Cave cards in your graveyard", FOR_EACH);

    public static Hint getHint() {
        return hint;
    }

    private final String number;
    private final String message;

    CavesControlledAndInGraveCount(String number, String message) {
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
    public CavesControlledAndInGraveCount copy() {
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
