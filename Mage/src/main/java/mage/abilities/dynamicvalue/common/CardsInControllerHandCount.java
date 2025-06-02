package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Controllable;
import mage.game.Game;
import mage.players.Player;

import java.util.Optional;
import java.util.Set;

public enum CardsInControllerHandCount implements DynamicValue {


    ANY(StaticFilters.FILTER_CARD_CARDS),
    ANY_SINGULAR(StaticFilters.FILTER_CARD),
    CREATURES(StaticFilters.FILTER_CARD_CREATURES),
    LANDS(StaticFilters.FILTER_CARD_LANDS);

    private final FilterCard filter;
    private final ValueHint hint;

    CardsInControllerHandCount(FilterCard filter) {
        this.filter = filter;
        this.hint = new ValueHint(filter.getMessage() + " in your hand", this);
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Optional
                .ofNullable(sourceAbility)
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .map(Player::getHand)
                .map(Set::size)
                .orElse(0);
    }

    @Override
    public CardsInControllerHandCount copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return this.filter.getMessage() + " in your hand";
    }

    @Override
    public String toString() {
        return "1";
    }

    public Hint getHint() {
        return this.hint;
    }
}
