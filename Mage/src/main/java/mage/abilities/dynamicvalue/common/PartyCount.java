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

    private static final List<SubType> partyTypes = Arrays.asList(
            SubType.CLERIC,
            SubType.ROGUE,
            SubType.WARRIOR,
            SubType.WIZARD
    );

    private static final class CreatureTypes {

        private final Set<SubType> subTypeSet = new HashSet<>();

        private CreatureTypes(Permanent permanent, Game game) {
            for (SubType subType : partyTypes) {
                if (permanent.hasSubtype(subType, game)) {
                    subTypeSet.add(subType);
                }
            }
        }

        private int count() {
            return subTypeSet.size();
        }

        private void removeAll(Set<SubType> subTypes) {
            this.subTypeSet.removeAll(subTypes);
        }

        public Set<SubType> getSubTypeSet() {
            return subTypeSet;
        }
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        List<CreatureTypes> creatureTypesList = game.getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_CREATURE,
                        sourceAbility.getControllerId(), sourceAbility.getSourceId(), game
                ).stream()
                .map(permanent -> new CreatureTypes(permanent, game))
                .collect(Collectors.toList());
        Set<SubType> subTypeSet = new HashSet<>();
        boolean flag = true;
        while (flag) {
            List<CreatureTypes> creatureTypesList2 = new ArrayList<>();
            // Find all creatures with only one of the creature types and eliminate those
            for (CreatureTypes creatureTypes : creatureTypesList) {
                switch (creatureTypes.count()) {
                    case 0:
                        break;
                    case 1:
                        subTypeSet.addAll(creatureTypes.getSubTypeSet());
                        flag = false;
                        break;
                    default:
                        creatureTypesList2.add(creatureTypes);
                }
            }
            // Now find all the creature types only represented by one creature and eliminate those
            for (SubType subType : partyTypes) {
                if (!subTypeSet.contains(subType) && creatureTypesList2.stream().map(CreatureTypes::getSubTypeSet).mapToInt(s -> s.contains(subType) ? 1 : 0).sum() == 1) {
                    subTypeSet.add(subType);
                    flag = false;
                }
            }
            // Remove all the eliminated creature types from the creatures and now we have less to analyze
            creatureTypesList.clear();
            for (CreatureTypes creatureTypes : creatureTypesList2) {
                creatureTypes.removeAll(subTypeSet);
                if (creatureTypes.count() > 0) {
                    creatureTypesList.add(creatureTypes);
                }
            }
        }
        if (creatureTypesList.isEmpty()) {
            return subTypeSet.size();
        }
        // Not sure what to do here
        return 0;
    }

    private static int typeCount(Permanent permanent, Game game) {
        return partyTypes.stream().mapToInt(subType -> permanent.hasSubtype(subType, game) ? 1 : 0).sum();
    }

    private static void addType(Permanent permanent, Set<SubType> subTypeSet, Game game) {
        for (SubType subType : partyTypes) {
            if (permanent.hasSubtype(subType, game)) {
                subTypeSet.add(subType);
            }
        }
    }

    @Override
    public PartyCount copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "for each creature in your party. <i>(Your party consists of up to one each of Cleric, Rogue, Warrior, and Wizard.)</i>";
    }

    @Override
    public String toString() {
        return "1";
    }
}
