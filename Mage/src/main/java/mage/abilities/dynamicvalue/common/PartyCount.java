package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public enum PartyCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        // TODO: implement this (in separate branch for now)
        return 0;
    }

    @Override
    public PartyCount copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "creature in your party. <i>(Your party consists of up to one each of Cleric, Rogue, Warrior, and Wizard.)</i>";
    }

    @Override
    public String toString() {
        return "1";
    }
}
