package mage.abilities.mana;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ManaEvent;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 *
 * this class is used to build a list of all possible mana combinations it can
 * be used to find all the ways to pay a mana cost or all the different mana
 * combinations available to a player
 *
 */
public class ManaOptions extends ArrayList<Mana> {

    private static final Logger logger = Logger.getLogger(ManaOptions.class);

    public ManaOptions() {
    }

    public ManaOptions(final ManaOptions options) {
        for (Mana mana : options) {
            this.add(mana.copy());
        }
    }

    public void addMana(List<ActivatedManaAbilityImpl> abilities, Game game) {
        if (isEmpty()) {
            this.add(new Mana());
        }
        if (!abilities.isEmpty()) {
            if (abilities.size() == 1) {
                //if there is only one mana option available add it to all the existing options
                List<Mana> netManas = abilities.get(0).getNetMana(game);
                if (netManas.size() == 1) {
                    if (!hasTapCost(abilities.get(0)) || checkTappedForManaReplacement(abilities.get(0), game, netManas.get(0))) {
                        addMana(netManas.get(0));
                    }
                } else {
                    List<Mana> copy = copy();
                    this.clear();
                    boolean hasTapCost = hasTapCost(abilities.get(0));
                    for (Mana netMana : netManas) {
                        for (Mana mana : copy) {
                            if (!hasTapCost /* || checkTappedForManaReplacement(abilities.get(0), game, netMana) */) { // Seems to produce endless iterations so deactivated for now:  https://github.com/magefree/mage/issues/5023
                                Mana newMana = new Mana();
                                newMana.add(mana);
                                newMana.add(netMana);
                                this.add(newMana);
                            }
                        }
                    }
                }

            } else { // mana source has more than 1 ability
                //perform a union of all existing options and the new options
                List<Mana> copy = copy();
                this.clear();
                for (ActivatedManaAbilityImpl ability : abilities) {
                    boolean hasTapCost = hasTapCost(ability);
                    for (Mana netMana : ability.getNetMana(game)) {
                        if (!hasTapCost || checkTappedForManaReplacement(ability, game, netMana)) {
                            SkipAddMana:
                            for (Mana mana : copy) {
                                Mana newMana = new Mana();
                                newMana.add(mana);
                                newMana.add(netMana);
                                for (Mana existingMana : this) {
                                    if (existingMana.equalManaValue(newMana)) {
                                        continue SkipAddMana;
                                    }
                                    Mana moreValuable = Mana.getMoreValuableMana(newMana, existingMana);
                                    if (moreValuable != null) {
                                        // only keep the more valuable mana
                                        existingMana.setToMana(moreValuable);
                                        continue SkipAddMana;
                                    }
                                }
                                this.add(newMana);
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean checkTappedForManaReplacement(Ability ability, Game game, Mana mana) {
        ManaEvent event = new ManaEvent(GameEvent.EventType.TAPPED_FOR_MANA, ability.getSourceId(), ability.getSourceId(), ability.getControllerId(), mana);
        if (!game.replaceEvent(event)) {
            return true;
        }
        return false;
    }

    private boolean hasTapCost(Ability ability) {
        for (Cost cost : ability.getCosts()) {
            if (cost instanceof TapSourceCost) {
                return true;
            }
        }
        return false;
    }

    public void addManaWithCost(List<ActivatedManaAbilityImpl> abilities, Game game) {
        int replaces = 0;
        if (isEmpty()) {
            this.add(new Mana());
        }
        if (!abilities.isEmpty()) {
            if (abilities.size() == 1) {
                //if there is only one mana option available add it to all the existing options
                ActivatedManaAbilityImpl ability = abilities.get(0);
                List<Mana> netManas = abilities.get(0).getNetMana(game);
                // no mana costs
                if (ability.getManaCosts().isEmpty()) {
                    if (netManas.size() == 1) {
                        addMana(netManas.get(0));
                    } else {
                        List<Mana> copy = copy();
                        this.clear();
                        for (Mana netMana : netManas) {
                            for (Mana mana : copy) {
                                Mana newMana = new Mana();
                                newMana.add(mana);
                                newMana.add(netMana);
                                this.add(newMana);
                            }
                        }
                    }
                } else // the ability has mana costs
                if (netManas.size() == 1) {
                    subtractCostAddMana(ability.getManaCosts().getMana(), netManas.get(0), ability.getCosts().isEmpty());
                } else {
                    List<Mana> copy = copy();
                    this.clear();
                    for (Mana netMana : netManas) {
                        for (Mana mana : copy) {
                            Mana newMana = new Mana();
                            newMana.add(mana);
                            newMana.add(netMana);
                            subtractCostAddMana(ability.getManaCosts().getMana(), netMana, ability.getCosts().isEmpty());
                        }
                    }
                }
            } else {
                //perform a union of all existing options and the new options
                List<Mana> copy = copy();
                this.clear();
                for (ActivatedManaAbilityImpl ability : abilities) {
                    boolean hasTapCost = hasTapCost(ability);
                    List<Mana> netManas = ability.getNetMana(game);

                    if (ability.getManaCosts().isEmpty()) {
                        for (Mana netMana : netManas) {
                            if (!hasTapCost || checkTappedForManaReplacement(ability, game, netMana)) {
                                for (Mana mana : copy) {
                                    Mana newMana = new Mana();
                                    newMana.add(mana);
                                    newMana.add(netMana);
                                    this.add(newMana);
                                }
                            }
                        }
                    } else {
                        for (Mana netMana : netManas) {
                            if (!hasTapCost || checkTappedForManaReplacement(ability, game, netMana)) {
                                for (Mana previousMana : copy) {
                                    CombineWithExisting:
                                    for (Mana manaOption : ability.getManaCosts().getManaOptions()) {
                                        Mana newMana = new Mana(previousMana);
                                        if (previousMana.includesMana(manaOption)) { // costs can be paid
                                            newMana.subtractCost(manaOption);
                                            newMana.add(netMana);
                                            // if the new mana is in all colors more than another already existing than replace
                                            for (Mana existingMana : this) {
                                                Mana moreValuable = Mana.getMoreValuableMana(newMana, existingMana);
                                                if (moreValuable != null) {
                                                    existingMana.setToMana(moreValuable);
                                                    replaces++;
                                                    continue CombineWithExisting;
                                                }
                                            }
                                            // no existing Mana includes this new mana so add
                                            this.add(newMana);
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
        if (this.size() > 30 || replaces > 30) {
            logger.trace("ManaOptionsCosts " + this.size() + " Ign:" + replaces + " => " + this.toString());
            logger.trace("Abilities: " + abilities.toString());
        }
    }

    public void addMana(Mana addMana) {
        if (isEmpty()) {
            this.add(new Mana());
        }
        for (Mana mana : this) {
            mana.add(addMana);
        }
    }

    public void addMana(ManaOptions options) {
        if (isEmpty()) {
            this.add(new Mana());
        }
        if (!options.isEmpty()) {
            if (options.size() == 1) {
                //if there is only one mana option available add it to all the existing options
                addMana(options.get(0));
            } else {
                //perform a union of all existing options and the new options
                List<Mana> copy = copy();
                this.clear();
                for (Mana addMana : options) {
                    for (Mana mana : copy) {
                        Mana newMana = new Mana();
                        newMana.add(mana);
                        newMana.add(addMana);
                        this.add(newMana);
                    }
                }
            }
        }
    }

    public ManaOptions copy() {
        return new ManaOptions(this);
    }

    public void subtractCostAddMana(Mana cost, Mana addMana, boolean onlyManaCosts) {
        if (isEmpty()) {
            this.add(new Mana());
        }
        boolean repeatable = false;
        if (addMana.getAny() == 1 && addMana.count() == 1 && onlyManaCosts) {
            // deactivated because it does cause loops TODO: Find reason
            repeatable = true; // only replace to any with mana costs only will be repeated if able
        }
        List<Mana> copy = copy();
        this.clear();
        for (Mana mana : copy) {
            Mana oldMan = mana.copy();
            if (mana.includesMana(cost)) { // it can be paid
                // generic mana costs can be paid with different colored mana, can lead to different color combinations
                if (cost.getGeneric() > 0 && cost.getGeneric() > (mana.getGeneric() + mana.getColorless())) {
                    Mana coloredCost = cost.copy();
                    coloredCost.setGeneric(0);
                    mana.subtract(coloredCost);
                    boolean oldManaWasReplaced = false;
                    for (Mana payCombination : getPossiblePayCombinations(cost.getGeneric(), mana)) {
                        Mana newMana = mana.copy();
                        newMana.subtract(payCombination);
                        newMana.add(addMana);
                        Mana moreValuable = Mana.getMoreValuableMana(oldMan, newMana);
                        if (!oldMan.equals(moreValuable)) {
                            this.add(newMana);
                            if (moreValuable != null) {
                                oldManaWasReplaced = true; // the new mana includes all possibilities of the old one
                            }
                        }

                    }
                    if (!oldManaWasReplaced) {
                        this.add(oldMan);
                    }
                } else {
                    while (mana.includesMana(cost)) {
                        mana.subtractCost(cost);
                        mana.add(addMana);
                        if (!repeatable) {
                            break;
                        }
                    }
                    // Don't use mana that only reduce the available mana
                    if (oldMan.contains(mana) && oldMan.count() > mana.count()) {
                        mana.setToMana(oldMan);
                    }
                    this.add(mana);
                }
            }
        }
    }

    private List<Mana> getPossiblePayCombinations(int number, Mana manaAvailable) {
        List<Mana> payCombinations = new ArrayList<>();
        List<String> payCombinationsStrings = new ArrayList<>();
        if (manaAvailable.countColored() > 0) {

            for (int i = 0; i < number; i++) {
                List<Mana> existingManas = new ArrayList<>();
                if (i > 0) {
                    existingManas.addAll(payCombinations);
                    payCombinations.clear();
                    payCombinationsStrings.clear();
                } else {
                    existingManas.add(new Mana());
                }
                for (Mana existingMana : existingManas) {
                    Mana manaToPayFrom = manaAvailable.copy();
                    manaToPayFrom.subtract(existingMana);
                    if (manaToPayFrom.getBlack() > 0 && !payCombinationsStrings.contains(existingMana.toString() + Mana.BlackMana(1).toString())) {
                        manaToPayFrom.subtract(Mana.BlackMana(1));
                        addManaCombination(Mana.BlackMana(1), existingMana, payCombinations, payCombinationsStrings);
                    }
                    if (manaToPayFrom.getBlue() > 0 && !payCombinationsStrings.contains(existingMana.toString() + Mana.BlueMana(1).toString())) {
                        manaToPayFrom.subtract(Mana.BlueMana(1));
                        addManaCombination(Mana.BlueMana(1), existingMana, payCombinations, payCombinationsStrings);
                    }
                    if (manaToPayFrom.getGreen() > 0 && !payCombinationsStrings.contains(existingMana.toString() + Mana.GreenMana(1).toString())) {
                        manaToPayFrom.subtract(Mana.GreenMana(1));
                        addManaCombination(Mana.GreenMana(1), existingMana, payCombinations, payCombinationsStrings);
                    }
                    if (manaToPayFrom.getRed() > 0 && !payCombinationsStrings.contains(existingMana.toString() + Mana.RedMana(1).toString())) {
                        manaToPayFrom.subtract(Mana.RedMana(1));
                        addManaCombination(Mana.RedMana(1), existingMana, payCombinations, payCombinationsStrings);
                    }
                    if (manaToPayFrom.getWhite() > 0 && !payCombinationsStrings.contains(existingMana.toString() + Mana.WhiteMana(1).toString())) {
                        manaToPayFrom.subtract(Mana.WhiteMana(1));
                        addManaCombination(Mana.WhiteMana(1), existingMana, payCombinations, payCombinationsStrings);
                    }
                    // Pay with any only needed if colored payment was not possible
                    if (payCombinations.isEmpty() && manaToPayFrom.getAny() > 0 && !payCombinationsStrings.contains(existingMana.toString() + Mana.AnyMana(1).toString())) {
                        manaToPayFrom.subtract(Mana.AnyMana(1));
                        addManaCombination(Mana.AnyMana(1), existingMana, payCombinations, payCombinationsStrings);
                    }
                }
            }
        } else {
            payCombinations.add(Mana.ColorlessMana(number));
        }
        return payCombinations;
    }

    private void addManaCombination(Mana mana, Mana existingMana, List<Mana> payCombinations, List<String> payCombinationsStrings) {
        Mana newMana = existingMana.copy();
        newMana.add(mana);
        payCombinations.add(newMana);
        payCombinationsStrings.add(newMana.toString());
    }

    public void removeDuplicated() {
        Set<String> list = new HashSet<>();

        for (int i = this.size() - 1; i >= 0; i--) {
            String s = this.get(i).toString();
            if (list.contains(s)) {
                // remove duplicated
                this.remove(i);
            } else {
                list.add(s);
            }
        }
        // Remove fully included variations
        for (int i = this.size() - 1; i >= 0; i--) {
            for (int ii = 0; ii < i; ii++) {
                Mana moreValuable = Mana.getMoreValuableMana(this.get(i), this.get(ii));
                if (moreValuable != null) {
                    this.get(ii).setToMana(moreValuable);
                    this.remove(i);
                    break;
                }
            }
        }
    }
}
