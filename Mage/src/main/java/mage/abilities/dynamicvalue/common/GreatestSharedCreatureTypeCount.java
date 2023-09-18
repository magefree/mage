package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.constants.SubType;
import mage.constants.SubTypeSet;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.*;

/**
 * @author TheElk801
 */
public enum GreatestSharedCreatureTypeCount implements DynamicValue {
    instance;

    private static final Hint hint = new ValueHint(
            "Greatest number of creatures you control that share a creature type", instance
    );

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return getValue(sourceAbility.getControllerId(), sourceAbility, game);
    }

    public static int getValue(UUID playerId, Ability source, Game game) {
        List<Permanent> permanentList = game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE, playerId, source, game
        );
        permanentList.removeIf(Objects::isNull);
        int changelings = permanentList
                .stream()
                .filter(Objects::nonNull)
                .filter(permanent1 -> permanent1.isAllCreatureTypes(game))
                .mapToInt(x -> 1)
                .sum();
        permanentList.removeIf(permanent1 -> permanent1.isAllCreatureTypes(game));
        Map<SubType, Integer> typeMap = new HashMap<>();
        permanentList
                .stream()
                .map(permanent -> permanent.getSubtype(game))
                .flatMap(Collection::stream)
                .filter(subType -> subType.getSubTypeSet() == SubTypeSet.CreatureType)
                .forEach(subType -> typeMap.compute(subType, CardUtil::setOrIncrementValue));
        return changelings
                + typeMap
                .values()
                .stream()
                .mapToInt(x -> x)
                .max()
                .orElse(0);
    }

    @Override
    public GreatestSharedCreatureTypeCount copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "greatest number of creatures you control that have a creature type in common";
    }

    public static Hint getHint() {
        return hint;
    }
}
