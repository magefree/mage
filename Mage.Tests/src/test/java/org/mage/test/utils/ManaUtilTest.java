package org.mage.test.utils;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.mana.*;
import mage.cards.Card;
import mage.cards.repository.CardRepository;
import mage.util.ManaUtil;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author noxx
 */
public class ManaUtilTest extends CardTestPlayerBase {

    @Test
    public void testAutoPay() {
        testManaToPayVsLand("{R}",                "Blood Crypt", 2, 1); // should use {R}
        testManaToPayVsLand("{1}{R}",             "Blood Crypt", 2, RedManaAbility.class); // should use {R}
        testManaToPayVsLand("{R}{B}",             "Blood Crypt", 2, 2); // can't auto choose to pay
        testManaToPayVsLand("{2}{R}{B}",          "Blood Crypt", 2, 2); // can't auto choose to pay
        testManaToPayVsLand("{R}{R}{B}{B}",       "Blood Crypt", 2, 2); // can't auto choose to pay
        testManaToPayVsLand("{R}{G}{W}{W}{U}",    "Blood Crypt", 2, RedManaAbility.class); // should use {R}
        testManaToPayVsLand("{R}{R}{G}{W}{W}{U}", "Blood Crypt", 2, RedManaAbility.class); // should use {R}
        testManaToPayVsLand("{R}{R}",             "Blood Crypt", 2, RedManaAbility.class); // should use {R}
        testManaToPayVsLand("{G}{W}",             "Blood Crypt", 2, 2); // can't auto choose to pay
        testManaToPayVsLand("{1}{G}{W}",          "Blood Crypt", 2, 1); // should use any but auto choose it
        testManaToPayVsLand("{2}{G}{W}{U}",       "Blood Crypt", 2, 1); // should use any but auto choose it
        testManaToPayVsLand("{3}",                "Blood Crypt", 2, 1); // should use any but auto choose it

        testManaToPayVsLand("{R}{R}{G}{W}{W}{U}", "Watery Grave",   2, 1); // should use {U}
        testManaToPayVsLand("{R}{R}{G}{W}{W}",    "Steam Vents",    2, 1); // should use {R}
        testManaToPayVsLand("{R}{R}{G}{B}{U}",    "Temple Garden",  2, 1); // should use {G}
        testManaToPayVsLand("{W}{W}{G}{B}{U}",    "Sacred Foundry", 2, 1); // should use {W}
        testManaToPayVsLand("{W}{W}{R}{B}{U}",    "Overgrown Tomb", 2, BlackManaAbility.class); // should use {B}
        testManaToPayVsLand("{W}{W}{R}{B}{U}",    "Swamp",          1, BlackManaAbility.class);
        testManaToPayVsLand("{W}{W}{R}{B}{U}",    "Plains",         1, WhiteManaAbility.class);

        testManaToPayVsLand("{1}{R}",   "Cavern of Souls", 2, 2); // can't auto choose to pay
        testManaToPayVsLand("{2}",      "Cavern of Souls", 2, 2); // can't auto choose to pay

        testManaToPayVsLand("{2}", "Eldrazi Temple", 2, 2); // can't auto choose to pay

        // hybrid mana
        testManaToPayVsLand("{W/R}{W/R}{W/R}",  "Sacred Foundry",   2, 1); // auto choose for hybrid mana: choose any
        testManaToPayVsLand("{R}{W/R}",         "Sacred Foundry",   2, RedManaAbility.class); // auto choose for hybrid mana: we should choose {R}
        testManaToPayVsLand("{G}{W/R}",         "Sacred Foundry",   2, 1); // auto choose for hybrid mana: choose any
        testManaToPayVsLand("{G}{W/R}{W}",      "Sacred Foundry",   2, WhiteManaAbility.class); // auto choose for hybrid mana: choose {W}
        testManaToPayVsLand("{W/B}{W/B}",       "Swamp",            1, BlackManaAbility.class);

        testManaToPayVsLand("{R}",      "Glimmervoid", 1, 1);
        testManaToPayVsLand("{R}{1}",   "Glimmervoid", 1, 1);

        // we can't auto choose here:
        // let say we auto choose {R}, then we have to use it to pay for {R} not {W/R} (as {W/R} is more generic cost)
        // but in such case what is left to pay is {W/R}{W} and it is possible that we won't have 2 white sources
        // Example: 1x Sacred Foundry 1x Mountain 1x Mountain
        // we can pay {W/R}{W}{R} by using Sacred Foundry and choosing {W} then using two Mountains
        // but if we auto choose {R} then we won't be able to pay the cost at all
        testManaToPayVsLand("{W/R}{W}{R}", "Sacred Foundry", 2, 2);

        testManaToPayVsLand("{W/R}{R/G}", "Sacred Foundry", 2, 2); // can't auto choose to pay
    }

    /**
     * Mana.condenseManaCostString is used to simplify the String representation of a mana cost to make it more readable.
     */
    @Test
    public void testManaCondensing() {
        Assert.assertEquals("{5}{W}",               ManaUtil.condenseManaCostString("{1}{1}{1}{2}{W}"));
        Assert.assertEquals("{4}{B}{B}",            ManaUtil.condenseManaCostString("{2}{B}{2}{B}"));
        Assert.assertEquals("{6}{R}{R}{R}{U}",      ManaUtil.condenseManaCostString("{R}{1}{R}{2}{R}{3}{U}"));
        Assert.assertEquals("{5}{B}{U}{W}",         ManaUtil.condenseManaCostString("{1}{B}{W}{4}{U}"));
        Assert.assertEquals("{8}{B}{G}{G}{U}",      ManaUtil.condenseManaCostString("{1}{G}{1}{2}{3}{G}{B}{U}{1}"));
        Assert.assertEquals("{3}{R}{U}",            ManaUtil.condenseManaCostString("{3}{R}{U}"));
        Assert.assertEquals("{10}",                 ManaUtil.condenseManaCostString("{1}{2}{3}{4}"));
        Assert.assertEquals("{B}{G}{R}{U}{W}",      ManaUtil.condenseManaCostString("{B}{G}{R}{U}{W}"));
        Assert.assertEquals("{R}{R}",               ManaUtil.condenseManaCostString("{R}{R}"));
        Assert.assertEquals("{U}",                  ManaUtil.condenseManaCostString("{U}"));
        Assert.assertEquals("{2}",                  ManaUtil.condenseManaCostString("{2}"));
        Assert.assertEquals("",                     ManaUtil.condenseManaCostString("{}"));
        Assert.assertEquals("{5}{C}{R}{R}{R}{U}",   ManaUtil.condenseManaCostString("{R}{C}{R}{2}{R}{3}{U}"));
    }

    @Test
    public void testUnpaidParts() {
        ManaCosts<ManaCost> cost = parse("{W}{W/U}{2/W}{C/W}{W/P}{C}{S}{1}");
        Assert.assertEquals("{W} {W/U} {2/W} {C/W} {W/P} {C} {S} {1}", texts(ManaUtil.getUnpaidParts(cost)));

        cost = parse("{W}{2/W}{3}");
        cost.get(0).setPayment(Mana.WhiteMana(1));
        cost.get(1).setPayment(Mana.ColorlessMana(1));
        cost.get(2).setPayment(Mana.ColorlessMana(3));
        Assert.assertEquals("{2/W}", texts(ManaUtil.getUnpaidParts(cost))); // paid parts are left out, partly paid ones kept

        cost = parse("{W}{U}");
        ManaUtil.getUnpaidParts(cost).get(0).setPayment(Mana.WhiteMana(1));
        Assert.assertEquals("{U}", cost.getUnpaid().getText()); // the parts are the cost's own

        // a single symbol, such as the unpaid part ManaCostImpl.pay hands to playMana
        GenericManaCost symbol = new GenericManaCost(2);
        Assert.assertEquals("{2}", texts(ManaUtil.getUnpaidParts(symbol)));
        symbol.setPayment(Mana.ColorlessMana(2));
        Assert.assertEquals("", texts(ManaUtil.getUnpaidParts(symbol)));
    }

    @Test
    public void testUnpaidGeneric() {
        Assert.assertEquals(3, ManaUtil.getUnpaidGeneric(parse("{3}{W}")));
        Assert.assertEquals(5, ManaUtil.getUnpaidGeneric(parse("{2/W}{2/W}{1}"))); // an unpaid {2/W} counts as {2}
        Assert.assertEquals(0, ManaUtil.getUnpaidGeneric(parse("{W/U}{C/W}{W/P}"))); // hybrid and phyrexian symbols are colored
        Assert.assertEquals(0, ManaUtil.getUnpaidGeneric(parse("{C}")));
        Assert.assertEquals(0, ManaUtil.getUnpaidGeneric(parse("{S}"))); // {S} isn't generic (107.4h), though SnowManaCost.getMana() is

        ManaCosts<ManaCost> partlyPaid = parse("{3}{2/W}{2/W}");
        partlyPaid.get(0).setPayment(Mana.ColorlessMana(1));
        partlyPaid.get(1).setPayment(Mana.ColorlessMana(1));
        partlyPaid.get(2).setPayment(Mana.WhiteMana(1));
        Assert.assertEquals(2, ManaUtil.getUnpaidGenericOfPart(partlyPaid.get(0)));
        Assert.assertEquals(1, ManaUtil.getUnpaidGenericOfPart(partlyPaid.get(1)));
        Assert.assertEquals(0, ManaUtil.getUnpaidGenericOfPart(parse("{W/U}").get(0)));
        Assert.assertEquals(3, ManaUtil.getUnpaidGeneric(partlyPaid)); // nothing left of the {2/W} paid with {W}
    }

    @Test
    public void testGenericOnly() {
        ManaCosts<ManaCost> cost = parse("{3}{2/W}{2/W}{W}{W/U}{C}{S}");
        cost.get(2).setPayment(Mana.ColorlessMana(1));
        Assert.assertTrue(ManaUtil.isGenericOnly(cost.get(0)));
        Assert.assertFalse(ManaUtil.isGenericOnly(cost.get(1))); // can still be paid with {W}
        Assert.assertTrue(ManaUtil.isGenericOnly(cost.get(2))); // a partly paid {2/W} can only be finished as generic
        for (ManaCost part : cost.subList(3, cost.size())) {
            Assert.assertFalse(part.getText(), ManaUtil.isGenericOnly(part));
        }
    }

    @Test
    public void testUnpaidGenericParts() {
        Assert.assertEquals("{2} {2/W}", texts(ManaUtil.getUnpaidGenericParts(parse("{2/W}{W}{2}{C}{S}{W/U}")))); // an untouched {2/W} goes last

        ManaCosts<ManaCost> cost = parse("{2/W}{2/W}{1}");
        cost.get(1).setPayment(Mana.ColorlessMana(1));
        cost.get(2).setPayment(Mana.ColorlessMana(1));
        List<ManaCost> parts = ManaUtil.getUnpaidGenericParts(cost);
        Assert.assertEquals(2, parts.size()); // the paid {1} is left out
        Assert.assertSame(cost.get(1), parts.get(0)); // the partly paid {2/W} is generic only, so it comes first
        Assert.assertSame(cost.get(0), parts.get(1));
    }

    @Test
    public void testPayGeneric() {
        ManaCosts<ManaCost> cost = parse("{3}{W}");
        ManaUtil.payGeneric(cost, 2, null);
        Assert.assertEquals("{1}{W}", cost.getUnpaid().getText());
        ManaUtil.payGeneric(cost, 1, null);
        Assert.assertEquals("{W}", cost.getUnpaid().getText());
        Assert.assertEquals(0, cost.getUsedManaToPay().count()); // no mana spent

        // generic-only parts are paid first, so an untouched {2/W} can still be paid with {W}
        cost = parse("{2/W}{2}");
        ManaUtil.payGeneric(cost, 2, null);
        Assert.assertEquals("{2/W}", cost.getUnpaid().getText());
        cost.get(0).setPayment(Mana.WhiteMana(1));
        Assert.assertTrue(cost.isPaid());

        cost = parse("{2/W}{2}");
        ManaUtil.payGeneric(cost, 3, null);
        Assert.assertEquals(1, ManaUtil.getUnpaidGeneric(cost));
        Assert.assertTrue(ManaUtil.isGenericOnly(cost.get(0)));

        cost = parse("{2/W}{2/W}");
        ManaUtil.payGeneric(cost, 3, null);
        Assert.assertEquals(1, ManaUtil.getUnpaidParts(cost).size());
        Assert.assertEquals(1, ManaUtil.getUnpaidGeneric(cost));

        cost = parse("{2}{1}");
        Mana spent = Mana.RedMana(1);
        spent.add(Mana.GreenMana(1));
        ManaUtil.payGeneric(cost, 2, spent);
        Assert.assertEquals(spent, cost.getUsedManaToPay());
    }

    /**
     * Common way to test ManaUtil.tryToAutoPay
     *
     * We get all mana abilities, then try to auto pay and compare to expected1
     * and expected2 params.
     *
     * @param manaToPay Mana that should be paid using land.
     * @param landName Land to use as mana producer.
     * @param expected1 The amount of mana abilities the land should have.
     * @param expected2 The amount of mana abilities that ManaUtil.tryToAutoPay
     * should be returned after optimization.
     */
    private void testManaToPayVsLand(String manaToPay, String landName, int expected1, int expected2) {
        ManaCost unpaid = new ManaCostsImpl<>(manaToPay);
        Card card = CardRepository.instance.findCard(landName).createCard();
        Assert.assertNotNull(card);

        Map<UUID, ActivatedManaAbilityImpl> useableAbilities = getManaAbilities(card);
        Assert.assertEquals(expected1, useableAbilities.size());

        useableAbilities = ManaUtil.tryToAutoPay(unpaid, (LinkedHashMap<UUID, ActivatedManaAbilityImpl>) useableAbilities);
        Assert.assertEquals(expected2, useableAbilities.size());
    }

    /**
     * Another way to test ManaUtil.tryToAutoPay Here we also check what ability
     * was auto chosen
     *
     * N.B. This method can be used ONLY if we have one ability left that auto
     * choose mode! That's why we assert the following: Assert.assertEquals(1,
     * useableAbilities.size());
     *
     * We get all mana abilities, then try to auto pay and compare to expected1
     * and expected2 params.
     *
     * @param manaToPay         Mana that should be paid using land.
     * @param landName          Land to use as mana producer.
     * @param expected1         The amount of mana abilities the land should have.
     * @param expectedChosen    The mana ability expected to be chosen.
     */
    private void testManaToPayVsLand(String manaToPay, String landName, int expected1, Class<? extends BasicManaAbility> expectedChosen) {
        ManaCost unpaid = new ManaCostsImpl<>(manaToPay);
        Card card = CardRepository.instance.findCard(landName).createCard();
        Assert.assertNotNull(card);

        Map<UUID, ActivatedManaAbilityImpl> useableAbilities = getManaAbilities(card);
        Assert.assertEquals(expected1, useableAbilities.size());

        useableAbilities = ManaUtil.tryToAutoPay(unpaid, (LinkedHashMap<UUID, ActivatedManaAbilityImpl>) useableAbilities);
        Assert.assertEquals(1, useableAbilities.size());
        ActivatedManaAbilityImpl ability = useableAbilities.values().iterator().next();
        Assert.assertTrue("Wrong mana ability has been chosen", expectedChosen.isInstance(ability));
    }

    /**
     * Extracts mana abilities from the card.
     *
     * @param card  Card to extract mana abilities from.
     * @return      Map between the UUID of each ability on the card and the ability.
     */
    private Map<UUID, ActivatedManaAbilityImpl> getManaAbilities(Card card) {
        Map<UUID, ActivatedManaAbilityImpl> useableAbilities = new LinkedHashMap<>();
        for (Ability ability : card.getAbilities()) {
            if (ability instanceof ActivatedManaAbilityImpl) {
                ability.newId(); // we need to assign id manually as we are not in game
                useableAbilities.put(ability.getId(), (ActivatedManaAbilityImpl) ability);
            }
        }
        return useableAbilities;
    }

    private static ManaCosts<ManaCost> parse(String text) {
        return new ManaCostsImpl<>(text);
    }

    private static String texts(List<ManaCost> parts) {
        return parts.stream().map(ManaCost::getText).collect(Collectors.joining(" "));
    }

}
