    /*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.mana;

import java.util.ArrayList;
import java.util.List;
import mage.Mana;
import mage.game.Game;

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

    public ManaOptions() {
    }

    ;

    public ManaOptions(final ManaOptions options) {
        for (Mana mana : options) {
            this.add(mana.copy());
        }
    }

    public void addMana(List<ManaAbility> abilities, Game game) {
        if (isEmpty()) {
            this.add(new Mana());
        }
        if (!abilities.isEmpty()) {
            if (abilities.size() == 1) {
                //if there is only one mana option available add it to all the existing options
                List<Mana> netManas = abilities.get(0).getNetMana(game);
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

            } else if (abilities.size() > 1) {
                //perform a union of all existing options and the new options
                List<Mana> copy = copy();
                this.clear();
                for (ManaAbility ability : abilities) {
                    for (Mana netMana : ability.getNetMana(game)) {
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

    public void addManaWithCost(List<ManaAbility> abilities, Game game) {
        if (isEmpty()) {
            this.add(new Mana());
        }
        if (!abilities.isEmpty()) {
            if (abilities.size() == 1) {
                //if there is only one mana option available add it to all the existing options
                ManaAbility ability = abilities.get(0);
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
                } else {
                    // the ability has mana costs
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
                }
            } else if (abilities.size() > 1) {
                //perform a union of all existing options and the new options
                List<Mana> copy = copy();
                this.clear();
                for (ManaAbility ability : abilities) {

                    List<Mana> netManas = ability.getNetMana(game);

                    if (ability.getManaCosts().isEmpty()) {
                        for (Mana netMana : netManas) {
                            for (Mana mana : copy) {
                                Mana newMana = new Mana();
                                newMana.add(mana);
                                newMana.add(netMana);
                                this.add(newMana);
                            }
                        }
                    } else {
                        for (Mana netMana : netManas) {
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
            } else if (options.size() > 1) {
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
            if (mana.includesMana(cost)) {
                // colorless costs can be paid with different colored mana, can lead to different color combinations
                if (cost.getColorless() > 0 && cost.getColorless() > mana.getColorless()) {
                    Mana coloredCost = cost.copy();
                    coloredCost.setColorless(0);
                    mana.subtract(coloredCost);
                    for (Mana payCombination : getPossiblePayCombinations(cost.getColorless(), mana)) {
                        Mana newMana = mana.copy();
                        newMana.subtract(payCombination);
                        newMana.add(addMana);
                        if (oldMan.contains(newMana) && oldMan.count() > newMana.count()) {
                            newMana.setToMana(oldMan);
                        }
                        this.add(newMana);
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
                    Mana manaToPay = manaAvailable.copy();
                    manaToPay.subtract(existingMana);
                    if (manaToPay.getBlack() > 0 && !payCombinationsStrings.contains(existingMana.toString() + Mana.BlackMana.toString())) {
                        manaToPay.subtract(Mana.BlackMana);
                        addManaCombination(Mana.BlackMana, existingMana, payCombinations, payCombinationsStrings);
                    }
                    if (manaToPay.getBlue() > 0 && !payCombinationsStrings.contains(existingMana.toString() + Mana.BlueMana.toString())) {
                        manaToPay.subtract(Mana.BlueMana);
                        addManaCombination(Mana.BlueMana, existingMana, payCombinations, payCombinationsStrings);
                    }
                    if (manaToPay.getGreen() > 0 && !payCombinationsStrings.contains(existingMana.toString() + Mana.GreenMana.toString())) {
                        manaToPay.subtract(Mana.GreenMana);
                        addManaCombination(Mana.GreenMana, existingMana, payCombinations, payCombinationsStrings);
                    }
                    if (manaToPay.getRed() > 0 && !payCombinationsStrings.contains(existingMana.toString() + Mana.RedMana.toString())) {
                        manaToPay.subtract(Mana.RedMana);
                        addManaCombination(Mana.RedMana, existingMana, payCombinations, payCombinationsStrings);
                    }
                    if (manaToPay.getWhite() > 0 && !payCombinationsStrings.contains(existingMana.toString() + Mana.WhiteMana.toString())) {
                        manaToPay.subtract(Mana.WhiteMana);
                        addManaCombination(Mana.WhiteMana, existingMana, payCombinations, payCombinationsStrings);
                    }
                }
            }
        } else {
            payCombinations.add(new Mana(0, 0, 0, 0, 0, number, 0));
        }
        return payCombinations;
    }

    private void addManaCombination(Mana mana, Mana existingMana, List<Mana> payCombinations, List<String> payCombinationsStrings) {
        Mana newMana = existingMana.copy();
        newMana.add(mana);
        payCombinations.add(newMana);
        payCombinationsStrings.add(newMana.toString());
    }
}
