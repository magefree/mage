package mage.util;

import mage.Mana;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.mana.*;

import java.util.LinkedHashMap;
import java.util.UUID;

/**
 * @author noxx
 */
public class ManaUtil {

    private ManaUtil() {}

    /**
     * In case the choice of mana to be produced is obvious, let's discard all other abilities.
     *
     * Example:
     *   Pay {W}{R}
     *
     *   Land produces {W} or {G}.
     *
     *   No need to ask what player wants to choose.
     *   {W} mana ability should be left only.
     *
     * But we CAN do auto choice only in case we have basic mana abilities.
     * Example:
     *   we should pay {1} and we have Cavern of Souls that can produce {1} or any mana of creature type choice.
     *   We can't simply auto choose {1} as the second mana ability also makes spell uncounterable.
     *
     * In case we can't auto choose we'll simply return the useableAbilities map back to caller without any modification.
     *
     * @param unpaid Mana we need to pay. Can be null (it is for X costs now).
     * @param useableAbilities List of mana abilities permanent may produce
     * @return List of mana abilities permanent may produce and are reasonable for unpaid mana
     */
    public static LinkedHashMap<UUID, ManaAbility> tryToAutoPay(ManaCost unpaid, LinkedHashMap<UUID, ManaAbility> useableAbilities) {
        if (unpaid != null)  {
            Mana mana = unpaid.getMana();
            // first check if we have only basic mana abilities
            for (ManaAbility ability : useableAbilities.values()) {
                if (!(ability instanceof BasicManaAbility)) {
                    // return map as-is without any modification
                    return useableAbilities;
                }
            }
            int countColorfull = 0;
            int countColorless = 0;
            ManaAbility chosenManaAbility = null;
            for (ManaAbility ability : useableAbilities.values()) {
                if (ability instanceof RedManaAbility && mana.contains(Mana.RedMana)) {
                    chosenManaAbility = ability;
                    countColorfull++;
                }
                if (ability instanceof BlackManaAbility && mana.contains(Mana.BlackMana)) {
                    chosenManaAbility = ability;
                    countColorfull++;
                }
                if (ability instanceof BlueManaAbility && mana.contains(Mana.BlueMana)) {
                    chosenManaAbility = ability;
                    countColorfull++;
                }
                if (ability instanceof WhiteManaAbility && mana.contains(Mana.WhiteMana)) {
                    chosenManaAbility = ability;
                    countColorfull++;
                }
                if (ability instanceof GreenManaAbility && mana.contains(Mana.GreenMana)) {
                    chosenManaAbility = ability;
                    countColorfull++;
                }
            }

            if (countColorfull == 0) { // seems there is no colorful mana we can use
                // try to pay {1}
                if (mana.getColorless() > 0) {
                    // use first
                    return replace(useableAbilities, useableAbilities.values().iterator().next());
                }
                
                // return map as-is without any modification
                return useableAbilities;
            }

            if (countColorfull > 1) { // we can't auto choose as there are variant of mana payment
                // return map as-is without any modification
                return useableAbilities;
            }

            return replace(useableAbilities, chosenManaAbility);
        }

        return useableAbilities;
    }

    private static LinkedHashMap<UUID, ManaAbility> replace(LinkedHashMap<UUID, ManaAbility> useableAbilities, ManaAbility chosenManaAbility) {
        // modify the map with the chosen mana ability
        useableAbilities.clear();
        useableAbilities.put(chosenManaAbility.getId(), chosenManaAbility);

        return useableAbilities;
    }
}
