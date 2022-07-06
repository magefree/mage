package mage.abilities.mana;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import mage.ConditionalMana;
import mage.Mana;
import mage.abilities.Ability;
import mage.constants.ManaType;
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
 * TODO: Conditional Mana is not supported yet.
 *       The mana adding removes the condition of conditional mana
 * <p>
 * A LinkedHashSet is used to get the performance benefits of automatic de-duplication of the Mana
 * to avoid performance issues related with manual de-duplication (see https://github.com/magefree/mage/issues/7710).
 *
 */
public class ManaOptions extends LinkedHashSet<Mana> {

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
        if (abilities.isEmpty()) {
            return; // Do nothing
        }

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
            Set<Mana> copy = copy();
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

    private void addManaVariation(List<Mana> netManas, ActivatedManaAbilityImpl ability, Game game) {
        Set<Mana> copy = copy();
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
        if (isEmpty()) {
            this.add(new Mana()); // needed if this is the first available mana, otherwise looping over existing options woold not loop
        }
        if (!abilities.isEmpty()) {
            if (abilities.size() == 1) {
                List<Mana> netManas = abilities.get(0).getNetMana(game);
                if (!netManas.isEmpty()) { // ability can produce mana
                    ActivatedManaAbilityImpl ability = abilities.get(0);
                    // The ability has no mana costs
                    if (ability.getManaCosts().isEmpty()) { // No mana costs, so no mana to subtract from available
                        if (netManas.size() == 1) {
                            checkManaReplacementAndTriggeredMana(ability, game, netManas.get(0));
                            addMana(netManas.get(0));
                            addTriggeredMana(game, ability);
                        } else {
                            Set<Mana> copy = copy();
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
                        Set<Mana> copy = copy();
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
                Set<Mana> copy = copy();
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

        if (logger.isTraceEnabled() && this.size() > 30) {
            logger.trace("ManaOptionsCosts " + this.size());
            logger.trace("Abilities: " + abilities.toString());
        }

        return wasUsable;
    }

    public boolean addManaPoolDependant(List<ActivatedManaAbilityImpl> abilities, Game game) {
        boolean wasUsable = false;
        if (!abilities.isEmpty()) {
            if (abilities.size() == 1) {
                ActivatedManaAbilityImpl ability = (ActivatedManaAbilityImpl) abilities.get(0);
                Set<Mana> copy = copy();
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
                Set<Mana> copy = copy();
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
    }

    public void addMana(ManaOptions options) {
        if (isEmpty()) {
            this.add(new Mana());
        }
        if (!options.isEmpty()) {
            if (options.size() == 1) {
                //if there is only one mana option available add it to all the existing options
                addMana(options.iterator().next());
            } else {
                //perform a union of all existing options and the new options
                Set<Mana> copy = copy();
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
            repeatable = true; // only replace to any with mana costs only will be repeated if able TODO: rewrite this comment
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

        return oldManaWasReplaced;
    }

    /**
     * @param manaCost
     * @param manaAvailable
     * @return
     */
    public static Set<Mana> getPossiblePayCombinations(Mana manaCost, Mana manaAvailable) {
        Set<Mana> payCombinations = new HashSet<>();

        Mana fixedMana = manaCost.copy();

        // TODO: What about Hybrid mana?
        // If there is no generic costs, then there is only one combination of colors available to pay for it.
        // That combination is itself (fixedMana)
        if (manaCost.getGeneric() == 0) {
            payCombinations.add(fixedMana);
            return payCombinations;
        }

        // Get the available mana left to pay for the cost after subtracting the non-generic parts of the cost from it
        fixedMana.setGeneric(0);
        Mana manaAfterFixedPayment = manaAvailable.copy();
        manaAfterFixedPayment.subtract(fixedMana);

        // handle generic mana costs
        if (manaAvailable.countColored() == 0) {
            payCombinations.add(Mana.ColorlessMana(manaCost.getGeneric())); // TODO: Is it guaranteed that there is enough colorless mana to satisfy this?
        } else {
            for (int i = 0; i < manaCost.getGeneric(); i++) {
                List<Mana> existingManas = new ArrayList<>();
                if (i > 0) {
                    existingManas.addAll(payCombinations);
                    payCombinations.clear();
                } else {
                    existingManas.add(new Mana());
                }

                for (Mana existingMana : existingManas) {
                    Mana manaToPayFrom = manaAfterFixedPayment.copy();
                    manaToPayFrom.subtract(existingMana);

                    for (ManaType manaType : ManaType.values()) {
                        existingMana.increase(manaType);
                        if (manaToPayFrom.get(manaType) > 0 && !payCombinations.contains(existingMana)) {
                            Mana newMana = existingMana.copy();
                            payCombinations.add(newMana);

                            manaToPayFrom.decrease(manaType);
                        }
                        existingMana.decrease(manaType);
                    }

                    // Pay with any only needed if colored payment was not possible
                    // NOTE: This isn't in the for loop since ManaType doesn't include ANY.
                    existingMana.increaseAny();
                    if (payCombinations.isEmpty() && manaToPayFrom.getAny() > 0) {
                        Mana newMana = existingMana.copy();
                        payCombinations.add(newMana);

                        manaToPayFrom.decreaseAny();
                    }
                    existingMana.decreaseAny();
                }
            }
        }

        for (Mana mana : payCombinations) {
            mana.add(fixedMana);
        }
        // All mana values in here are of length 5
        return payCombinations;
    }

    private boolean isExistingManaCombination(Mana newMana) {
        for (Mana mana : this) {
            Mana moreValuable = Mana.getMoreValuableMana(mana, newMana);  // TODO: WTF is this
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


    /**
     * Remove fully included variations. If both {R} and {R}{W} are in this, then {R} will be removed.
     * TODO: Why is this needed
     */
    public void removeFullyIncludedVariations() {
        ArrayList<Mana> m = new ArrayList<>(this); // Shallow

        for (int i = m.size() - 1; i >= 0; i--) {
            for (int ii = 0; ii < i; ii++) {
                Mana moreValuable = Mana.getMoreValuableMana(m.get(i), m.get(ii));
                if (moreValuable != null) {
                    m.get(ii).setToMana(moreValuable);
                    this.remove(m.get(i));
                    m.remove(i);
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

    /**
     * Utility function to get a Mana from ManaOptions at the specified position.
     * Since the implementation uses a LinkedHashSet the ordering of the items is preserved.
     *
     * NOTE: Do not use in tight loops as performance of the lookup is much worse than
     *       for ArrayList (the previous superclass of ManaOptions).
     *
     * NOTE: Deprecated warning is there as a reminder for when this gets called that it's not
     *       the usual get() function from an array.
     *
     */
    @Deprecated
    public Mana get(int i) {
       if (i < 0 || i >= this.size()) {
           throw new IndexOutOfBoundsException();
       }
       Iterator<Mana> itr = this.iterator();
       while(itr.hasNext()) {
           if (i == 0) {
               return itr.next();
           } else {
               itr.next(); // Ignore the value
               i--;
           }
       }
       return null; // Not sure how we'd ever get here, but leave just in case since IDE complains.
    }

    /**
     * Overriden here in order to avoid it silently failing if someone provides it an index
     * thinking that this now works like an array
     *
     * @param o object to be removed from this set, if present
     * @return
     */
    @Override
    public boolean remove(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Mana)) {
            throw new ValueException("Can only remove Mana from ManaOptions, provided " + o);
        }
        return super.remove(o);
    }
}
