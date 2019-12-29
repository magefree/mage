package mage.util;

import mage.MageObject;
import mage.Mana;
import mage.ManaSymbol;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.*;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.mana.*;
import mage.cards.Card;
import mage.choices.Choice;
import mage.constants.ColoredManaSymbol;
import mage.filter.FilterMana;
import mage.game.Game;
import mage.players.Player;

import java.util.*;

/**
 * @author noxx
 */
public final class ManaUtil {

    private ManaUtil() {
    }

    /**
     * In case the choice of mana to be produced is obvious, let's discard all
     * other abilities.
     * <p>
     * Example: Pay {W}{R}
     * <p>
     * Land produces {W} or {G}.
     * <p>
     * No need to ask what player wants to choose. {W} mana ability should be
     * left only.
     * <p>
     * But we CAN do auto choice only in case we have basic mana abilities.
     * Example: we should pay {1} and we have Cavern of Souls that can produce
     * {1} or any mana of creature type choice. We can't simply auto choose {1}
     * as the second mana ability also makes spell uncounterable.
     * <p>
     * In case we can't auto choose we'll simply return the useableAbilities map
     * back to caller without any modification.
     *
     * @param unpaid           Mana we need to pay. Can be null (it is for X costs now).
     * @param useableAbilities List of mana abilities permanent may produce
     * @return List of mana abilities permanent may produce and are reasonable
     * for unpaid mana
     */
    public static LinkedHashMap<UUID, ActivatedManaAbilityImpl> tryToAutoPay(ManaCost unpaid, LinkedHashMap<UUID, ActivatedManaAbilityImpl> useableAbilities) {

        // first check if we have only basic mana abilities
        for (ActivatedManaAbilityImpl ability : useableAbilities.values()) {
            if (!(ability instanceof BasicManaAbility)) {
                // return map as-is without any modification
                if (!(ability instanceof AnyColorManaAbility)) {
                    return useableAbilities;
                }
            }
        }

        if (unpaid != null) {
            ManaSymbols symbols = ManaSymbols.buildFromManaCost(unpaid);
            Mana unpaidMana = unpaid.getMana();

            if (!symbols.isEmpty()) {
                return getManaAbilitiesUsingManaSymbols(useableAbilities, symbols, unpaidMana);
            } else {
                return getManaAbilitiesUsingMana(unpaid, useableAbilities);
            }
        }

        return useableAbilities;
    }

    /**
     * For Human players this is called before a player is asked to select a
     * mana color to pay a specific cost. If the choice obvious, the color is
     * auto picked by this method without bothering the human player
     *
     * @param choice the color choice to do
     * @param unpaid the mana still to pay
     * @return
     */
    public static boolean tryToAutoSelectAManaColor(Choice choice, ManaCost unpaid) {
        String colorToAutoPay = null;
        if (unpaid.containsColor(ColoredManaSymbol.W) && choice.getChoices().contains("White")) {
            colorToAutoPay = "White";
        }
        if (unpaid.containsColor(ColoredManaSymbol.R) && choice.getChoices().contains("Red")) {
            if (colorToAutoPay != null) {
                return false;
            }
            colorToAutoPay = "Red";
        }
        if (unpaid.containsColor(ColoredManaSymbol.G) && choice.getChoices().contains("Green")) {
            if (colorToAutoPay != null) {
                return false;
            }
            colorToAutoPay = "Green";
        }
        if (unpaid.containsColor(ColoredManaSymbol.U) && choice.getChoices().contains("Blue")) {
            if (colorToAutoPay != null) {
                return false;
            }
            colorToAutoPay = "Blue";
        }
        if (unpaid.containsColor(ColoredManaSymbol.B) && choice.getChoices().contains("Black")) {
            if (colorToAutoPay != null) {
                return false;
            }
            colorToAutoPay = "Black";
        }
        // only colorless to pay so take first choice
        if (unpaid.getMana().getDifferentColors() == 0) {
            colorToAutoPay = choice.getChoices().iterator().next();
        }
        // one possible useful option found
        if (colorToAutoPay != null) {
            choice.setChoice(colorToAutoPay);
            return true;
        }
        return false;
    }

    private static LinkedHashMap<UUID, ActivatedManaAbilityImpl> getManaAbilitiesUsingManaSymbols(LinkedHashMap<UUID, ActivatedManaAbilityImpl> useableAbilities, ManaSymbols symbols, Mana unpaidMana) {
        Set<ManaSymbol> countColored = new HashSet<>();

        ActivatedManaAbilityImpl chosenManaAbility = null;
        ActivatedManaAbilityImpl chosenManaAbilityForHybrid;
        for (ActivatedManaAbilityImpl ability : useableAbilities.values()) {
            chosenManaAbility = getManaAbility(symbols, countColored, chosenManaAbility, ability);

            chosenManaAbilityForHybrid = checkRedMana(symbols, countColored, ability);
            chosenManaAbility = chosenManaAbilityForHybrid != null ? chosenManaAbilityForHybrid : chosenManaAbility;
            chosenManaAbilityForHybrid = checkBlackMana(symbols, countColored, ability);
            chosenManaAbility = chosenManaAbilityForHybrid != null ? chosenManaAbilityForHybrid : chosenManaAbility;
            chosenManaAbilityForHybrid = checkBlueMana(symbols, countColored, ability);
            chosenManaAbility = chosenManaAbilityForHybrid != null ? chosenManaAbilityForHybrid : chosenManaAbility;
            chosenManaAbilityForHybrid = checkWhiteMana(symbols, countColored, ability);
            chosenManaAbility = chosenManaAbilityForHybrid != null ? chosenManaAbilityForHybrid : chosenManaAbility;
            chosenManaAbilityForHybrid = checkGreenMana(symbols, countColored, ability);
            chosenManaAbility = chosenManaAbilityForHybrid != null ? chosenManaAbilityForHybrid : chosenManaAbility;
        }

        if (countColored.isEmpty()) { // seems there is no colorful mana we can pay for
            // try to pay {1}
            if (unpaidMana.getGeneric() > 0) {
                // use any (lets choose first)
                return replace(useableAbilities, useableAbilities.values().iterator().next());
            }

            // return map as-is without any modification
            return useableAbilities;
        }

        if (countColored.size() > 1) {
            // we may try to pay for hybrid mana symbol
            Set<ManaSymbol> temp = new HashSet<>();
            temp.addAll(countColored);
            for (ManaSymbol manaSymbol : countColored) {
                // idea: if we have {W/R} symbol then we can remove it if symbols contain {W} or {R}
                // but only if it doesn't contain both of them
                if (manaSymbol.isHybrid()) {
                    boolean found1 = countColored.contains(manaSymbol.getManaSymbol1());
                    boolean found2 = countColored.contains(manaSymbol.getManaSymbol2());
                    if (found1 && !found2) {
                        temp.remove(manaSymbol);
                    } else if (!found1 && found2) {
                        temp.remove(manaSymbol);
                    }
                }
            }

            // we got another chance for auto pay
            if (temp.size() == 1) {
                for (ActivatedManaAbilityImpl ability : useableAbilities.values()) {
                    chosenManaAbility = getManaAbility(symbols, countColored, chosenManaAbility, ability);
                }
                return replace(useableAbilities, chosenManaAbility);
            }

            // we can't auto choose as there are variants of mana payment
            // return map as-is without any modification
            return useableAbilities;
        }

        return replace(useableAbilities, chosenManaAbility);
    }

    private static ActivatedManaAbilityImpl getManaAbility(ManaSymbols symbols, Set<ManaSymbol> countColored, ActivatedManaAbilityImpl chosenManaAbility, ActivatedManaAbilityImpl ability) {
        if (ability instanceof RedManaAbility && symbols.contains(ManaSymbol.R)) {
            chosenManaAbility = ability;
            countColored.add(ManaSymbol.R);
        }
        if (ability instanceof BlackManaAbility && symbols.contains(ManaSymbol.B)) {
            chosenManaAbility = ability;
            countColored.add(ManaSymbol.B);
        }
        if (ability instanceof BlueManaAbility && symbols.contains(ManaSymbol.U)) {
            chosenManaAbility = ability;
            countColored.add(ManaSymbol.U);
        }
        if (ability instanceof WhiteManaAbility && symbols.contains(ManaSymbol.W)) {
            chosenManaAbility = ability;
            countColored.add(ManaSymbol.W);
        }
        if (ability instanceof GreenManaAbility && symbols.contains(ManaSymbol.G)) {
            chosenManaAbility = ability;
            countColored.add(ManaSymbol.G);
        }
        return chosenManaAbility;
    }

    /**
     * Counts DIFFERENT hybrid mana symbols.
     *
     * @param symbols
     * @return
     */
    private static int countUniqueHybridSymbols(Set<ManaSymbol> symbols) {
        int count = 0;
        for (ManaSymbol symbol : symbols) {
            if (symbol.isHybrid()) {
                count++;
            }
        }
        return count;
    }

    private static ActivatedManaAbilityImpl checkBlackMana(ManaSymbols symbols, Set<ManaSymbol> countColored, ActivatedManaAbilityImpl ability) {
        ActivatedManaAbilityImpl chosenManaAbilityForHybrid = null;
        if (ability instanceof BlackManaAbility) {
            if (symbols.contains(ManaSymbol.HYBRID_BR)) {
                chosenManaAbilityForHybrid = ability;
                countColored.add(ManaSymbol.HYBRID_BR);
            } else if (symbols.contains(ManaSymbol.HYBRID_BG)) {
                chosenManaAbilityForHybrid = ability;
                countColored.add(ManaSymbol.HYBRID_BG);
            } else if (symbols.contains(ManaSymbol.HYBRID_UB)) {
                chosenManaAbilityForHybrid = ability;
                countColored.add(ManaSymbol.HYBRID_UB);
            } else if (symbols.contains(ManaSymbol.HYBRID_WB)) {
                chosenManaAbilityForHybrid = ability;
                countColored.add(ManaSymbol.HYBRID_WB);
            }
        }

        return chosenManaAbilityForHybrid;
    }

    private static ActivatedManaAbilityImpl checkRedMana(ManaSymbols symbols, Set<ManaSymbol> countColored, ActivatedManaAbilityImpl ability) {
        ActivatedManaAbilityImpl chosenManaAbilityForHybrid = null;
        if (ability instanceof RedManaAbility) {
            if (symbols.contains(ManaSymbol.HYBRID_BR)) {
                chosenManaAbilityForHybrid = ability;
                countColored.add(ManaSymbol.HYBRID_BR);
            } else if (symbols.contains(ManaSymbol.HYBRID_RG)) {
                chosenManaAbilityForHybrid = ability;
                countColored.add(ManaSymbol.HYBRID_RG);
            } else if (symbols.contains(ManaSymbol.HYBRID_RW)) {
                chosenManaAbilityForHybrid = ability;
                countColored.add(ManaSymbol.HYBRID_RW);
            } else if (symbols.contains(ManaSymbol.HYBRID_UR)) {
                chosenManaAbilityForHybrid = ability;
                countColored.add(ManaSymbol.HYBRID_UR);
            }
        }
        return chosenManaAbilityForHybrid;
    }

    private static ActivatedManaAbilityImpl checkBlueMana(ManaSymbols symbols, Set<ManaSymbol> countColored, ActivatedManaAbilityImpl ability) {
        ActivatedManaAbilityImpl chosenManaAbilityForHybrid = null;
        if (ability instanceof BlueManaAbility) {
            if (symbols.contains(ManaSymbol.HYBRID_UB)) {
                chosenManaAbilityForHybrid = ability;
                countColored.add(ManaSymbol.HYBRID_UB);
            } else if (symbols.contains(ManaSymbol.HYBRID_UR)) {
                chosenManaAbilityForHybrid = ability;
                countColored.add(ManaSymbol.HYBRID_UR);
            } else if (symbols.contains(ManaSymbol.HYBRID_WU)) {
                chosenManaAbilityForHybrid = ability;
                countColored.add(ManaSymbol.HYBRID_WU);
            } else if (symbols.contains(ManaSymbol.HYBRID_GU)) {
                chosenManaAbilityForHybrid = ability;
                countColored.add(ManaSymbol.HYBRID_GU);
            }
        }
        return chosenManaAbilityForHybrid;
    }

    private static ActivatedManaAbilityImpl checkWhiteMana(ManaSymbols symbols, Set<ManaSymbol> countColored, ActivatedManaAbilityImpl ability) {
        ActivatedManaAbilityImpl chosenManaAbilityForHybrid = null;
        if (ability instanceof WhiteManaAbility) {
            if (symbols.contains(ManaSymbol.HYBRID_WU)) {
                chosenManaAbilityForHybrid = ability;
                countColored.add(ManaSymbol.HYBRID_WU);
            } else if (symbols.contains(ManaSymbol.HYBRID_WB)) {
                chosenManaAbilityForHybrid = ability;
                countColored.add(ManaSymbol.HYBRID_WB);
            } else if (symbols.contains(ManaSymbol.HYBRID_GW)) {
                chosenManaAbilityForHybrid = ability;
                countColored.add(ManaSymbol.HYBRID_GW);
            } else if (symbols.contains(ManaSymbol.HYBRID_RW)) {
                chosenManaAbilityForHybrid = ability;
                countColored.add(ManaSymbol.HYBRID_RW);
            }
        }
        return chosenManaAbilityForHybrid;
    }

    private static ActivatedManaAbilityImpl checkGreenMana(ManaSymbols symbols, Set<ManaSymbol> countColored, ActivatedManaAbilityImpl ability) {
        ActivatedManaAbilityImpl chosenManaAbilityForHybrid = null;
        if (ability instanceof GreenManaAbility) {
            if (symbols.contains(ManaSymbol.HYBRID_GW)) {
                chosenManaAbilityForHybrid = ability;
                countColored.add(ManaSymbol.HYBRID_GW);
            } else if (symbols.contains(ManaSymbol.HYBRID_GU)) {
                chosenManaAbilityForHybrid = ability;
                countColored.add(ManaSymbol.HYBRID_GU);
            } else if (symbols.contains(ManaSymbol.HYBRID_BG)) {
                chosenManaAbilityForHybrid = ability;
                countColored.add(ManaSymbol.HYBRID_BG);
            } else if (symbols.contains(ManaSymbol.HYBRID_RG)) {
                chosenManaAbilityForHybrid = ability;
                countColored.add(ManaSymbol.HYBRID_RG);
            }
        }
        return chosenManaAbilityForHybrid;
    }

    /**
     * This is old method that uses unpaid mana to filter out some abilities.
     * The only disadvantage is that it can't handle hybrid mana correctly.
     *
     * @param unpaid
     * @param useableAbilities
     * @return
     */
    private static LinkedHashMap<UUID, ActivatedManaAbilityImpl> getManaAbilitiesUsingMana(ManaCost unpaid, LinkedHashMap<UUID, ActivatedManaAbilityImpl> useableAbilities) {
        Mana mana = unpaid.getMana();

        int countColorfull = 0;
        int countColorless = 0;
        ActivatedManaAbilityImpl chosenManaAbility = null;
        for (ActivatedManaAbilityImpl ability : useableAbilities.values()) {
            if (ability instanceof RedManaAbility && mana.contains(Mana.RedMana(1))) {
                chosenManaAbility = ability;
                countColorfull++;
            }
            if (ability instanceof BlackManaAbility && mana.contains(Mana.BlackMana(1))) {
                chosenManaAbility = ability;
                countColorfull++;
            }
            if (ability instanceof BlueManaAbility && mana.contains(Mana.BlueMana(1))) {
                chosenManaAbility = ability;
                countColorfull++;
            }
            if (ability instanceof WhiteManaAbility && mana.contains(Mana.WhiteMana(1))) {
                chosenManaAbility = ability;
                countColorfull++;
            }
            if (ability instanceof GreenManaAbility && mana.contains(Mana.GreenMana(1))) {
                chosenManaAbility = ability;
                countColorfull++;
            }
        }

        if (countColorfull == 0) { // seems there is no colorful mana we can use
            // try to pay {1}
            if (mana.getGeneric() > 0) {
                // use any (lets choose first)
                return replace(useableAbilities, useableAbilities.values().iterator().next());
            }

            // return map as-is without any modification
            return useableAbilities;
        }

        if (countColorfull > 1) { // we can't auto choose as there are variants of mana payment
            // return map as-is without any modification
            return useableAbilities;
        }

        return replace(useableAbilities, chosenManaAbility);
    }

    private static LinkedHashMap<UUID, ActivatedManaAbilityImpl> replace(LinkedHashMap<UUID, ActivatedManaAbilityImpl> useableAbilities, ActivatedManaAbilityImpl chosenManaAbility) {
        // modify the map with the chosen mana ability
        useableAbilities.clear();
        useableAbilities.put(chosenManaAbility.getId(), chosenManaAbility);

        return useableAbilities;
    }

    /**
     * This activates the special button inthe feedback panel of the client if
     * there exists special ways to pay the mana (e.g. Delve, Convoke)
     *
     * @param source ability the mana costs have to be paid for
     * @param game
     * @param unpaid mana that has still to be paid
     * @return message to be shown in human players feedback area
     */
    public static String addSpecialManaPayAbilities(Ability source, Game game, ManaCost unpaid) {
        MageObject baseObject = game.getPermanent(source.getSourceId());
        if (baseObject == null) {
            baseObject = game.getCard(source.getSourceId());
        }
        // check for special mana payment possibilities
        MageObject mageObject = source.getSourceObject(game);
        if (mageObject instanceof Card) {
            for (Ability ability : ((Card) mageObject).getAbilities(game)) {
                if (ability instanceof AlternateManaPaymentAbility) {
                    ((AlternateManaPaymentAbility) ability).addSpecialAction(source, game, unpaid);
                }
            }
            if (baseObject == null) {
                baseObject = mageObject;
            }
        }
        if (baseObject != null) {
            return unpaid.getText() + "<div style='font-size:11pt'>" + baseObject.getLogName() + "</div>";
        } else {
            return unpaid.getText();
        }
    }

    /**
     * Converts a collection of mana symbols into a single condensed string e.g.
     * {1}{1}{1}{1}{1}{W} = {5}{W} {2}{B}{2}{B}{2}{B} = {6}{B}{B}{B}
     * {1}{2}{R}{U}{1}{1} = {5}{R}{U} {B}{G}{R} = {B}{G}{R}
     */
    public static String condenseManaCostString(String rawCost) {
        int total = 0;
        int index = 0;
        // Split the string in to an array of numbers and colored mana symbols
        String[] splitCost = rawCost.replace("{", "").replace("}", " ").split(" ");
        // Sort alphabetically which will push1 the numbers to the front before the colored mana symbols
        Arrays.sort(splitCost);
        for (String c : splitCost) {
            // If the string is a representation of a number
            if (c.matches("\\d+")) {
                total += Integer.parseInt(c);
            } else {
                // First non-number we see we can finish as they are sorted
                break;
            }
            index++;
        }
        int splitCostLength = splitCost.length;
        // No need to add {total} to the mana cost if total == 0
        int shift = (total > 0) ? 1 : 0;
        String[] finalCost = new String[shift + splitCostLength - index];
        // Account for no colourless mana symbols seen
        if (total > 0) {
            finalCost[0] = String.valueOf(total);
        }
        System.arraycopy(splitCost, index, finalCost, shift, splitCostLength - index);
        // Combine the cost back as a mana string
        StringBuilder sb = new StringBuilder();
        for (String s : finalCost) {
            sb.append('{').append(s).append('}');
        }
        // Return the condensed string
        return sb.toString();
    }

    public static boolean isColorIdentityCompatible(FilterMana needColors, FilterMana cardColors) {
        // colorless can be used with any color
        return needColors != null
                && !(cardColors.isBlack() && !needColors.isBlack()
                || cardColors.isBlue() && !needColors.isBlue()
                || cardColors.isGreen() && !needColors.isGreen()
                || cardColors.isRed() && !needColors.isRed()
                || cardColors.isWhite() && !needColors.isWhite());
    }

    public static void collectColorIdentity(FilterMana destColors, FilterMana newColors) {
        if (newColors.isWhite()) {
            destColors.setWhite(true);
        }
        if (newColors.isBlue()) {
            destColors.setBlue(true);
        }
        if (newColors.isBlack()) {
            destColors.setBlack(true);
        }
        if (newColors.isRed()) {
            destColors.setRed(true);
        }
        if (newColors.isGreen()) {
            destColors.setGreen(true);
        }
    }

    /**
     * all ability/effect code with "= new GenericManaCost" must be replaced by createManaCost call
     */
    public static ManaCost createManaCost(int genericManaCount, boolean payAsX) {
        if (payAsX) {
            VariableManaCost xCost = new VariableManaCost();
            xCost.setAmount(genericManaCount, genericManaCount, false);
            return xCost;
        } else {
            return new GenericManaCost(genericManaCount);
        }
    }

    public static ManaCost createManaCost(DynamicValue genericManaCount, Game game, Ability sourceAbility, Effect effect) {
        int costValue = genericManaCount.calculate(game, sourceAbility, effect);
        if (genericManaCount instanceof ManacostVariableValue) {
            // variable (X must be final value after all events and effects)
            return createManaCost(costValue, true);
        } else {
            // static/generic
            return createManaCost(costValue, false);
        }
    }

    public static int playerPaysXGenericMana(boolean payAsX, String restoreContextName, Player player, Ability source, Game game) {
        // payAsX - if your cost is X value (some mana can be used for X cost only)
        // false: "you may pay any amount of mana"
        // true: "counter that spell unless that player pays {X}"

        int wantToPay = 0;
        boolean payed = false;
        if (player.canRespond()) {
            int bookmark = game.bookmarkState();
            player.resetStoredBookmark(game);

            wantToPay = player.announceXMana(0, Integer.MAX_VALUE, "How much mana will you pay?", game, source);
            if (wantToPay > 0) {
                Cost cost = ManaUtil.createManaCost(wantToPay, payAsX);
                payed = cost.pay(source, game, source.getSourceId(), player.getId(), false, null);
            } else {
                payed = true;
            }

            if (!payed) {
                game.restoreState(bookmark, restoreContextName);
                game.fireUpdatePlayersEvent();
            } else {
                game.removeBookmark(bookmark);
            }
        }

        if (payed) {
            game.informPlayers(player.getLogName() + " pays {" + wantToPay + "}.");
            return wantToPay;
        } else {
            return 0;
        }

    }
}
