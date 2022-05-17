package mage.abilities.mana;

import mage.ConditionalMana;
import mage.Mana;
import mage.abilities.Ability;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ManaEvent;
import mage.game.events.TappedForManaEvent;
import mage.players.Player;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * @author BetaSteward_at_googlemail.com
 * <p>
 * this class is used to build a list of all possible mana combinations it can
 * be used to find all the ways to pay a mana cost or all the different mana
 * combinations available to a player
 * <p>
 * TODO: Conditional Mana is not supported yet. The mana adding removes the
 * condition of conditional mana
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
                    checkManaReplacementAndTriggeredMana(abilities.get(0), game, netManas.get(0));
                    addMana(netManas.get(0));
                    addTriggeredMana(game, abilities.get(0));
                } else if (netManas.size() > 1) {
                    addManaVariation(netManas, abilities.get(0), game);
                }

            } else { // mana source has more than 1 ability
                //perform a union of all existing options and the new options
                List<Mana> copy = copy();
                this.clear();
                for (ActivatedManaAbilityImpl ability : abilities) {
                    for (Mana netMana : ability.getNetMana(game)) {
                        checkManaReplacementAndTriggeredMana(ability, game, netMana);
                        for (Mana triggeredManaVariation : getTriggeredManaVariations(game, ability, netMana)) {
                            SkipAddMana:
                            for (Mana mana : copy) {
                                Mana newMana = new Mana();
                                newMana.add(mana);
                                newMana.add(triggeredManaVariation);
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

        forceManaDeduplication();
    }

    private void addManaVariation(List<Mana> netManas, ActivatedManaAbilityImpl ability, Game game) {
        List<Mana> copy = copy();
        this.clear();
        for (Mana netMana : netManas) {
            for (Mana mana : copy) {
                if (!ability.hasTapCost() || checkManaReplacementAndTriggeredMana(ability, game, netMana)) {
                    Mana newMana = new Mana();
                    newMana.add(mana);
                    newMana.add(netMana);
                    this.add(newMana);
                }
            }
        }

        forceManaDeduplication();
    }

    private static List<List<Mana>> getSimulatedTriggeredManaFromPlayer(Game game, Ability ability) {
        Player player = game.getPlayer(ability.getControllerId());
        List<List<Mana>> newList = new ArrayList<>();
        if (player != null) {
            newList.addAll(player.getAvailableTriggeredMana());
            player.getAvailableTriggeredMana().clear();
        }
        return newList;
    }

    /**
     * Generates triggered mana and checks replacement of Tapped_For_Mana event.
     * Also generates triggered mana for MANA_ADDED event.
     *
     * @param ability
     * @param game
     * @param mana
     * @return false if mana production was completely replaced
     */
    private boolean checkManaReplacementAndTriggeredMana(Ability ability, Game game, Mana mana) {
        if (ability.hasTapCost()) {
            ManaEvent event = new TappedForManaEvent(ability.getSourceId(), ability, ability.getControllerId(), mana, game);
            if (game.replaceEvent(event)) {
                return false;
            }
            game.fireEvent(event);
        }
        ManaEvent manaEvent = new ManaEvent(GameEvent.EventType.MANA_ADDED, ability.getSourceId(), ability, ability.getControllerId(), mana);
        manaEvent.setData(mana.toString());
        game.fireEvent(manaEvent);
        return true;
    }

    /**
     * This adds the mana the abilities can produce to the possible mana
     * variabtion.
     *
     * @param abilities
     * @param game
     * @return false if the costs could not be paid
     */
    public boolean addManaWithCost(List<ActivatedManaAbilityImpl> abilities, Game game) {
        boolean wasUsable = false;
        int replaces = 0;
        if (isEmpty()) {
            this.add(new Mana()); // needed if this is the first available mana, otherwise looping over existing options woold not loop
        }
        if (!abilities.isEmpty()) {
            if (abilities.size() == 1) {
                List<Mana> netManas = abilities.get(0).getNetMana(game);
                if (netManas.size() > 0) { // ability can produce mana
                    ActivatedManaAbilityImpl ability = abilities.get(0);
                    // The ability has no mana costs
                    if (ability.getManaCosts().isEmpty()) { // No mana costs, so no mana to subtract from available
                        if (netManas.size() == 1) {
                            checkManaReplacementAndTriggeredMana(ability, game, netManas.get(0));
                            addMana(netManas.get(0));
                            addTriggeredMana(game, ability);
                        } else {
                            List<Mana> copy = copy();
                            this.clear();
                            for (Mana netMana : netManas) {
                                checkManaReplacementAndTriggeredMana(ability, game, netMana);
                                for (Mana triggeredManaVariation : getTriggeredManaVariations(game, ability, netMana)) {
                                    for (Mana mana : copy) {
                                        Mana newMana = new Mana();
                                        newMana.add(mana);
                                        newMana.add(triggeredManaVariation);
                                        this.add(newMana);
                                        wasUsable = true;
                                    }
                                }
                            }
                        }
                    } else {// The ability has mana costs
                        List<Mana> copy = copy();
                        this.clear();
                        for (Mana netMana : netManas) {
                            checkManaReplacementAndTriggeredMana(ability, game, netMana);
                            for (Mana triggeredManaVariation : getTriggeredManaVariations(game, ability, netMana)) {
                                for (Mana prevMana : copy) {
                                    Mana startingMana = prevMana.copy();
                                    Mana manaCosts = ability.getManaCosts().getMana();
                                    if (startingMana.includesMana(manaCosts)) { // can pay the mana costs to use the ability
                                        if (!subtractCostAddMana(manaCosts, triggeredManaVariation, ability.getCosts().isEmpty(), startingMana, ability, game)) {
                                            // the starting mana includes mana parts that the increased mana does not include, so add starting mana also as an option
                                            add(prevMana);
                                        }
                                        wasUsable = true;
                                    } else {
                                        // mana costs can't be paid so keep starting mana
                                        add(prevMana);
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                //perform a union of all existing options and the new options
                List<Mana> copy = copy();
                this.clear();
                for (ActivatedManaAbilityImpl ability : abilities) {
                    List<Mana> netManas = ability.getNetMana(game);
                    if (ability.getManaCosts().isEmpty()) {
                        for (Mana netMana : netManas) {
                            checkManaReplacementAndTriggeredMana(ability, game, netMana);
                            for (Mana triggeredManaVariation : getTriggeredManaVariations(game, ability, netMana)) {
                                for (Mana mana : copy) {
                                    Mana newMana = new Mana();
                                    newMana.add(mana);
                                    newMana.add(triggeredManaVariation);
                                    this.add(newMana);
                                    wasUsable = true;
                                }
                            }
                        }
                    } else {
                        for (Mana netMana : netManas) {
                            checkManaReplacementAndTriggeredMana(ability, game, netMana);
                            for (Mana triggeredManaVariation : getTriggeredManaVariations(game, ability, netMana)) {
                                for (Mana previousMana : copy) {
                                    CombineWithExisting:
                                    for (Mana manaOption : ability.getManaCosts().getManaOptions()) {
                                        if (previousMana.includesMana(manaOption)) { // costs can be paid
                                            wasUsable |= subtractCostAddMana(manaOption, triggeredManaVariation, ability.getCosts().isEmpty(), previousMana, ability, game);
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
        forceManaDeduplication();

        return wasUsable;
    }

    public boolean addManaPoolDependant(List<ActivatedManaAbilityImpl> abilities, Game game) {
        boolean wasUsable = false;
        if (!abilities.isEmpty()) {
            if (abilities.size() == 1) {
                ActivatedManaAbilityImpl ability = (ActivatedManaAbilityImpl) abilities.get(0);
                List<Mana> copy = copy();
                this.clear();
                for (Mana previousMana : copy) {
                    Mana startingMana = previousMana.copy();
                    Mana manaCosts = ability.getManaCosts().getMana();
                    if (startingMana.includesMana(manaCosts)) { // can pay the mana costs to use the ability
                        for (Mana manaOption : ability.getManaCosts().getManaOptions()) {
                            if (!subtractCostAddMana(manaOption, null, ability.getCosts().isEmpty(), startingMana, ability, game)) {
                                // the starting mana includes mana parts that the increased mana does not include, so add starting mana also as an option
                                add(previousMana);
                            }
                        }
                        wasUsable = true;
                    } else {
                        // mana costs can't be paid so keep starting mana
                        add(previousMana);
                    }
                }

            }
        }
        return wasUsable;
    }

    public static List<Mana> getTriggeredManaVariations(Game game, Ability ability, Mana baseMana) {
        List<Mana> baseManaPlusTriggeredMana = new ArrayList<>();
        baseManaPlusTriggeredMana.add(baseMana);
        List<List<Mana>> availableTriggeredManaList = ManaOptions.getSimulatedTriggeredManaFromPlayer(game, ability);
        for (List<Mana> availableTriggeredMana : availableTriggeredManaList) {
            if (availableTriggeredMana.size() == 1) {
                for (Mana prevMana : baseManaPlusTriggeredMana) {
                    prevMana.add(availableTriggeredMana.get(0));
                }
            } else if (availableTriggeredMana.size() > 1) {
                List<Mana> copy = new ArrayList<>(baseManaPlusTriggeredMana);
                baseManaPlusTriggeredMana.clear();
                for (Mana triggeredMana : availableTriggeredMana) {
                    for (Mana prevMana : copy) {
                        Mana newMana = new Mana();
                        newMana.add(prevMana);
                        newMana.add(triggeredMana);
                        baseManaPlusTriggeredMana.add(newMana);
                    }
                }
            }
        }
        return baseManaPlusTriggeredMana;
    }

    private void addTriggeredMana(Game game, Ability ability) {
        List<List<Mana>> netManaList = getSimulatedTriggeredManaFromPlayer(game, ability);
        for (List<Mana> triggeredNetMana : netManaList) {
            if (triggeredNetMana.size() == 1) {
                addMana(triggeredNetMana.get(0));
            } else if (triggeredNetMana.size() > 1) {
                // Add variations
                List<Mana> copy = copy();
                this.clear();
                for (Mana triggeredMana : triggeredNetMana) {
                    for (Mana mana : copy) {
                        Mana newMana = new Mana();
                        newMana.add(mana);
                        newMana.add(triggeredMana);
                        this.add(newMana);
                    }
                }
            }
        }

        forceManaDeduplication();
    }

    /**
     * Adds the given mana value to all existing options
     *
     * @param addMana Mana to add to the existing options
     */
    public void addMana(Mana addMana) {
        if (isEmpty()) {
            this.add(new Mana());
        }
        if (addMana instanceof ConditionalMana) {
            ManaOptions copy = this.copy();
            this.clear();
            for (Mana mana : copy) {
                ConditionalMana condMana = ((ConditionalMana) addMana).copy();
                condMana.add(mana);
                add(condMana); // Add mana as option with condition
                add(mana); // Add old mana without the condition
            }

        } else {
            for (Mana mana : this) {
                mana.add(addMana);
            }
        }

        forceManaDeduplication();
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

        forceManaDeduplication();
    }

    private void forceManaDeduplication() {
        // memory overflow protection - force de-duplication on too much mana sources
        // bug example: https://github.com/magefree/mage/issues/6938
        // use it after new mana adding
        if (this.size() > 1000) {
            this.removeDuplicated();
        }
    }

    public ManaOptions copy() {
        return new ManaOptions(this);
    }

    /**
     * Performs the simulation of a mana ability with costs
     *
     * @param cost               cost to use the ability
     * @param manaToAdd          one mana variation that can be added by using
     *                           this ability
     * @param onlyManaCosts      flag to know if the costs are mana costs only
     * @param currentMana        the mana available before the usage of the
     *                           ability
     * @param oldManaWasReplaced returns the info if the new complete mana does
     *                           replace the current mana completely
     */
    private boolean subtractCostAddMana(Mana cost, Mana manaToAdd, boolean onlyManaCosts, Mana currentMana, ManaAbility manaAbility, Game game) {
        boolean oldManaWasReplaced = false; // true if the newly created mana includes all mana possibilities of the old
        boolean repeatable = false;
        if (manaToAdd != null && (manaToAdd.countColored() > 0 || manaToAdd.getAny() > 0) && manaToAdd.count() > 0 && onlyManaCosts) {
            repeatable = true; // only replace to any with mana costs only will be repeated if able
        }

        for (Mana payCombination : ManaOptions.getPossiblePayCombinations(cost, currentMana)) {
            Mana currentManaCopy = currentMana.copy(); // copy start mana because in iteration it will be updated
            while (currentManaCopy.includesMana(payCombination)) { // loop for multiple usage if possible
                boolean newCombinations = false;

                if (manaToAdd == null) {
                    Mana newMana = currentManaCopy.copy();
                    newMana.subtract(payCombination);
                    for (Mana mana : manaAbility.getNetMana(game, newMana)) { // get the mana to add from the ability related to the currently generated possible mana pool
                        newMana.add(mana);
                        if (!isExistingManaCombination(newMana)) {
                            this.add(newMana); // add the new combination
                            newCombinations = true; // repeat the while as long there are new combinations and usage is repeatable

                            Mana moreValuable = Mana.getMoreValuableMana(currentManaCopy, newMana);
                            if (newMana.equals(moreValuable)) {
                                oldManaWasReplaced = true; // the new mana includes all possibilities of the old one, so no need to add it after return
                                if (!currentMana.equalManaValue(currentManaCopy)) {
                                    this.removeEqualMana(currentManaCopy);
                                }
                            }
                            currentManaCopy = newMana.copy();
                        }
                    }
                } else {
                    Mana newMana = currentManaCopy.copy();
                    newMana.subtract(payCombination);
                    newMana.add(manaToAdd);
                    if (!isExistingManaCombination(newMana)) {
                        this.add(newMana); // add the new combination
                        newCombinations = true; // repeat the while as long there are new combinations and usage is repeatable                            
                        Mana moreValuable = Mana.getMoreValuableMana(currentManaCopy, newMana);
                        if (newMana.equals(moreValuable)) {
                            oldManaWasReplaced = true; // the new mana includes all possible mana of the old one, so no need to add it after return
                            if (!currentMana.equalManaValue(currentManaCopy)) {
                                this.removeEqualMana(currentManaCopy);
                            }
                        }
                        currentManaCopy = newMana.copy();
                    }
                }
                if (!newCombinations || !repeatable) {
                    break;
                }
            }

        }
        forceManaDeduplication();

        return oldManaWasReplaced;
    }

    /**
     * @param manaCost
     * @param manaAvailable
     * @return
     */
    public static List<Mana> getPossiblePayCombinations(Mana manaCost, Mana manaAvailable) {
        List<Mana> payCombinations = new ArrayList<>();
        List<String> payCombinationsStrings = new ArrayList<>();
        // handle fixed mana costs        
        Mana fixedMana = manaCost.copy();
        if (manaCost.getGeneric() == 0) {
            payCombinations.add(fixedMana);
            return payCombinations;
        }
        fixedMana.setGeneric(0);
        Mana manaAfterFixedPayment = manaAvailable.copy();
        manaAfterFixedPayment.subtract(fixedMana);

        // handle generic mana costs
        if (manaAvailable.countColored() > 0) {

            for (int i = 0; i < manaCost.getGeneric(); i++) {
                List<Mana> existingManas = new ArrayList<>();
                if (i > 0) {
                    existingManas.addAll(payCombinations);
                    payCombinations.clear();
                    payCombinationsStrings.clear();
                } else {
                    existingManas.add(new Mana());
                }
                for (Mana existingMana : existingManas) {
                    Mana manaToPayFrom = manaAfterFixedPayment.copy();
                    manaToPayFrom.subtract(existingMana);
                    String manaString = existingMana.toString();

                    if (manaToPayFrom.getBlack() > 0 && !payCombinationsStrings.contains(manaString + Mana.BlackMana(1))) {
                        manaToPayFrom.subtract(Mana.BlackMana(1));
                        ManaOptions.addManaCombination(Mana.BlackMana(1), existingMana, payCombinations, payCombinationsStrings);
                    }
                    if (manaToPayFrom.getBlue() > 0 && !payCombinationsStrings.contains(manaString + Mana.BlueMana(1).toString())) {
                        manaToPayFrom.subtract(Mana.BlueMana(1));
                        ManaOptions.addManaCombination(Mana.BlueMana(1), existingMana, payCombinations, payCombinationsStrings);
                    }
                    if (manaToPayFrom.getGreen() > 0 && !payCombinationsStrings.contains(manaString + Mana.GreenMana(1).toString())) {
                        manaToPayFrom.subtract(Mana.GreenMana(1));
                        ManaOptions.addManaCombination(Mana.GreenMana(1), existingMana, payCombinations, payCombinationsStrings);
                    }
                    if (manaToPayFrom.getRed() > 0 && !payCombinationsStrings.contains(manaString + Mana.RedMana(1).toString())) {
                        manaToPayFrom.subtract(Mana.RedMana(1));
                        ManaOptions.addManaCombination(Mana.RedMana(1), existingMana, payCombinations, payCombinationsStrings);
                    }
                    if (manaToPayFrom.getWhite() > 0 && !payCombinationsStrings.contains(manaString + Mana.WhiteMana(1).toString())) {
                        manaToPayFrom.subtract(Mana.WhiteMana(1));
                        ManaOptions.addManaCombination(Mana.WhiteMana(1), existingMana, payCombinations, payCombinationsStrings);
                    }
                    if (manaToPayFrom.getColorless() > 0 && !payCombinationsStrings.contains(manaString + Mana.ColorlessMana(1).toString())) {
                        manaToPayFrom.subtract(Mana.ColorlessMana(1));
                        ManaOptions.addManaCombination(Mana.ColorlessMana(1), existingMana, payCombinations, payCombinationsStrings);
                    }
                    // Pay with any only needed if colored payment was not possible
                    if (payCombinations.isEmpty() && manaToPayFrom.getAny() > 0 && !payCombinationsStrings.contains(manaString + Mana.AnyMana(1).toString())) {
                        manaToPayFrom.subtract(Mana.AnyMana(1));
                        ManaOptions.addManaCombination(Mana.AnyMana(1), existingMana, payCombinations, payCombinationsStrings);
                    }
                }
            }
        } else {
            payCombinations.add(Mana.ColorlessMana(manaCost.getGeneric()));
        }
        for (Mana mana : payCombinations) {
            mana.add(fixedMana);
        }
        return payCombinations;
    }

    private boolean isExistingManaCombination(Mana newMana) {
        for (Mana mana : this) {
            Mana moreValuable = Mana.getMoreValuableMana(mana, newMana);
            if (mana.equals(moreValuable)) {
                return true;
            }
        }
        return false;
    }

    public boolean removeEqualMana(Mana manaToRemove) {
        boolean result = false;
        for (Iterator<Mana> iterator = this.iterator(); iterator.hasNext(); ) {
            Mana next = iterator.next();
            if (next.equalManaValue(manaToRemove)) {
                iterator.remove();
                result = true;
            }
        }
        return result;
    }

    public static void addManaCombination(Mana mana, Mana existingMana, List<Mana> payCombinations, List<String> payCombinationsStrings) {
        Mana newMana = existingMana.copy();
        newMana.add(mana);
        payCombinations.add(newMana);
        payCombinationsStrings.add(newMana.toString());
    }

    public void removeDuplicated() {
        Set<String> list = new HashSet<>();

        for (int i = this.size() - 1; i >= 0; i--) {
            String s;
            if (this.get(i) instanceof ConditionalMana) {
                s = this.get(i).toString() + ((ConditionalMana) this.get(i)).getConditionString();
            } else {
                s = this.get(i).toString();
            }
            if (s.isEmpty()) {
                this.remove(i);
            } else if (list.contains(s)) {
                // remove duplicated
                this.remove(i);
            } else {
                list.add(s);
            }
        }

        // Remove fully included variations
        // TODO: research too many manas and freeze (put 1 card to slow down, put 3 cards to freeze here)
        //  battlefield:Human:Cascading Cataracts:1
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

    /**
     * Checks if the given mana (cost) is already included in one available mana
     * option
     *
     * @param mana
     * @return
     */
    public boolean enough(Mana mana) {
        // 117.5. Some costs are represented by {0}, or are reduced to {0}. The action necessary for a player to pay
        // such a cost is the player’s acknowledgment that he or she is paying it. Even though such a cost requires
        // no resources, it’s not automatically paid.
        if (mana.count() == 0) {
            return true;
        }

        for (Mana avail : this) {
            if (mana.enough(avail)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        Iterator<Mana> it = this.iterator();
        if (!it.hasNext()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (; ; ) {
            Mana mana = it.next();
            sb.append(mana.toString());
            if (mana instanceof ConditionalMana) {
                sb.append(((ConditionalMana) mana).getConditionString());
            }
            if (!it.hasNext()) {
                return sb.append(']').toString();
            }
            sb.append(',').append(' ');
        }
    }
}
