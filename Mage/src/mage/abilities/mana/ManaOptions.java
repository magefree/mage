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
 * this class is used to build a list of all possible mana combinations
 * it can be used to find all the ways to pay a mana cost
 * or all the different mana combinations available to a player
 *
 */
public class ManaOptions extends ArrayList<Mana> {

    public ManaOptions () {};

    public ManaOptions(final ManaOptions options) {
        for (Mana mana: options) {
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
                List<Mana> netManas =  abilities.get(0).getNetMana(game);
                if (netManas.size() == 1) {
                    addMana(netManas.get(0));
                } else {
                    List<Mana> copy = copy();
                    this.clear();
                    for (Mana netMana: netManas) {
                        for (Mana mana: copy) {
                            Mana newMana = new Mana();
                            newMana.add(mana);
                            newMana.add(netMana);
                            this.add(newMana);
                        }                        
                    }                    
                }
                
            }
            else if (abilities.size() > 1) {
                //perform a union of all existing options and the new options
                List<Mana> copy = copy();
                this.clear();
                for (ManaAbility ability: abilities) {
                    for (Mana netMana: ability.getNetMana(game)) {
                        SkipAddMana:
                        for (Mana mana: copy) {
                            Mana newMana = new Mana();
                            newMana.add(mana);
                            newMana.add(netMana);
                            for(Mana existingMana: this) {
                                if (existingMana.equalManaValue(newMana)) {
                                   continue SkipAddMana;
                                }
                                Mana moreValuable = Mana.getMoreValuableMana(newMana, existingMana);
                                if (moreValuable != null) {
                                    // only keep the more valuable mana
                                    existingMana.setToMana(newMana);
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
                List<Mana> netManas =  abilities.get(0).getNetMana(game);
                // no mana costs
                if (ability.getManaCosts().isEmpty()) {
                    if (netManas.size() == 1) {
                        addMana(netManas.get(0));
                    } else {
                        List<Mana> copy = copy();
                        this.clear();
                        for (Mana netMana: netManas) {
                            for (Mana mana: copy) {
                                Mana newMana = new Mana();
                                newMana.add(mana);
                                newMana.add(netMana);
                                this.add(newMana);
                            }                        
                        }                    
                    }                    
                }
                else {                    
                    if (netManas.size() == 1) {
                        subtractCostAddMana(ability.getManaCosts().getMana(), netManas.get(0), ability.getCosts().isEmpty());
                    } else {
                        List<Mana> copy = copy();
                        this.clear();
                        for (Mana netMana: netManas) {
                            for (Mana mana: copy) {
                                Mana newMana = new Mana();
                                newMana.add(mana);
                                newMana.add(netMana);
                                subtractCostAddMana(ability.getManaCosts().getMana(), netMana, ability.getCosts().isEmpty());
                            }                        
                        }                    
                    }                                        
                }
            }
            else if (abilities.size() > 1) {
                //perform a union of all existing options and the new options
                List<Mana> copy = copy();
                this.clear();
                for (ManaAbility ability: abilities) {
                    List<Mana> netManas =  ability.getNetMana(game);
                    if (ability.getManaCosts().isEmpty()) {
                        for (Mana netMana: netManas) {
                            for (Mana mana: copy) {
                                Mana newMana = new Mana();
                                newMana.add(mana);
                                newMana.add(netMana);
                                this.add(newMana);
                            }
                        }
                    }
                    else {
                        for (Mana netMana: netManas) {
                            for (Mana mana: copy) {                            
                                Mana newMana = new Mana();
                                newMana.add(mana);
                                if (mana.contains(ability.getManaCosts().getMana())) {
                                    newMana.subtract(ability.getManaCosts().getMana());
                                    newMana.add(netMana);
                                }
                                this.add(newMana);
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
        for (Mana mana: this) {
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
            }
            else if (options.size() > 1) {
                //perform a union of all existing options and the new options
                List<Mana> copy = copy();
                this.clear();
                for (Mana addMana: options) {
                    for (Mana mana: copy) {
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
        boolean addAny = false;
        if (addMana.getAny() == 1 && addMana.count() == 1) {
            addAny = true; // only replace to any will be repeated
        }
        for (Mana mana: this) {
            while (mana.includesMana(cost)) {
                mana.subtract(cost);
                mana.add(addMana);
                if (!addAny) {
                    break;
                }
            }
        }
    }

}