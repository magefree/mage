
package org.mage.test.cards.mana;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class NykthosShrineToNyxTest extends CardTestPlayerBase {

    @Test
    public void testNormalUse() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Nykthos, Shrine to Nyx", 1);
        // Kiora's Follower {G}{U}
        // Creature - Merfolk
        // {T}: Untap another target permanent.
        addCard(Zone.BATTLEFIELD, playerA, "Kiora's Follower");
        addCard(Zone.BATTLEFIELD, playerA, "Kalonian Tusker", 2);
        // Green mana doesn't empty from your mana pool as steps and phases end.
        // Omnath, Locus of Mana gets +1/+1 for each green mana in your mana pool.
        addCard(Zone.BATTLEFIELD, playerA, "Omnath, Locus of Mana", 1);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}: Choose a color. Add an amount of mana of that color equal to your devotion to that color.");
        setChoice(playerA, "Green");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        Assert.assertEquals("message", 6, playerA.getManaPool().getGreen()); // 6 green mana
        assertPowerToughness(playerA, "Omnath, Locus of Mana", 7, 7);
    }

    @Test
    public void testDoubleUse() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Nykthos, Shrine to Nyx", 1);
        // Kiora's Follower {G}{U}
        // Creature - Merfolk
        // {T}: Untap another target permanent.
        addCard(Zone.BATTLEFIELD, playerA, "Kiora's Follower");
        addCard(Zone.BATTLEFIELD, playerA, "Kalonian Tusker", 2);
        // Green mana doesn't empty from your mana pool as steps and phases end.
        // Omnath, Locus of Mana gets +1/+1 for each green mana in your mana pool.
        addCard(Zone.BATTLEFIELD, playerA, "Omnath, Locus of Mana", 1);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}: Choose a color. Add an amount of mana of that color equal to your devotion to that color.");
        setChoice(playerA, "Green");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Untap another target permanent.", "Nykthos, Shrine to Nyx");

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}: Choose a color. Add an amount of mana of that color equal to your devotion to that color.");
        setChoice(playerA, "Green");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        Assert.assertEquals("amount of green mana", 10, playerA.getManaPool().getGreen()); // 6G - 2G = 4G + 6G = 10G
        assertPowerToughness(playerA, "Omnath, Locus of Mana", 11, 11);
    }

    /*
        Use Nykthos together with Kruphix, God of Horizons to save mana as colorless mana
     */
    @Test
    public void testDoubleUseWithKruphix() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2); // to use Nykthos
        addCard(Zone.BATTLEFIELD, playerA, "Nykthos, Shrine to Nyx", 1);
        // Kiora's Follower {G}{U}
        // Creature - Merfolk
        // {T}: Untap another target permanent.
        addCard(Zone.BATTLEFIELD, playerA, "Kiora's Follower"); // 1 G devotion
        addCard(Zone.BATTLEFIELD, playerA, "Kalonian Tusker", 2); // 4 G devotion
        // Kruphix, God of Horizons {3}{G}{U}
        // If unused mana would empty from your mana pool, that mana becomes colorless instead.
        addCard(Zone.BATTLEFIELD, playerA, "Kruphix, God of Horizons", 1); // 1 G devotion

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}: Choose a color. Add an amount of mana of that color equal to your devotion to that color.");
        setChoice(playerA, "Green");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Untap another target permanent.", "Nykthos, Shrine to Nyx");

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}: Choose a color. Add an amount of mana of that color equal to your devotion to that color.");
        setChoice(playerA, "Green");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        Assert.assertEquals("amount of colorless mana", 10, playerA.getManaPool().getColorless()); // 6 - 2 (2.Activation) = 4 + 6  = 10 colorless mana
        assertPowerToughness(playerA, "Kruphix, God of Horizons", 4, 7);
    }

    @Test
    public void testNormalUseWithTokens() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        // {T}: Add {C}.
        // {2}, {T}: Choose a color. Add an amount of mana of that color equal to your devotion to that color.
        addCard(Zone.BATTLEFIELD, playerA, "Nykthos, Shrine to Nyx", 1);
        // Green mana doesn't empty from your mana pool as steps and phases end.
        // Omnath, Locus of Mana gets +1/+1 for each green mana in your mana pool.
        addCard(Zone.BATTLEFIELD, playerA, "Omnath, Locus of Mana", 1);
        // Simic Guildmage {G/U}{G/U}
        // Creature - Elf Wizard
        // {1}{G}: Move a +1/+1 counter from target creature onto another target creature with the same controller.
        // {1}{U}: Attach target Aura enchanting a permanent to another permanent with the same controller.
        addCard(Zone.BATTLEFIELD, playerA, "Simic Guildmage");
        // Cackling Counterpart {1}{U}{U}
        // Instant
        // Create a tokenonto the battlefield that's a copy of target creature you control.
        // Flashback {5}{U}{U} (You may cast this card from your graveyard for its flashback cost. Then exile it.)
        addCard(Zone.HAND, playerA, "Cackling Counterpart");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cackling Counterpart");
        addTarget(playerA, "Simic Guildmage");

        activateManaAbility(1, PhaseStep.BEGIN_COMBAT, playerA, "{2}, {T}: Choose a color. Add an amount of mana of that color equal to your devotion to that color.");
        setChoice(playerA, "Green");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Simic Guildmage", 2);
        Assert.assertEquals("amount of green mana", 5, playerA.getManaPool().getGreen()); // 5 green mana
        assertPowerToughness(playerA, "Omnath, Locus of Mana", 6, 6);
    }

    @Test
    public void testNykthosDevotionAccurate() {

        /*
            Nykthos, Shrine to Nyx
            Legendary Land
            {T}: Add {1}.
            {2}, {T}: Choose a color. Add an amount of mana of that color equal to your devotion to that color.
         */
        String nykthos = "Nykthos, Shrine to Nyx";

        /*
             Stronghold Assassin {1}{B}{B}
            Creature — Zombie Assassin 2/1
            {T}, Sacrifice a creature: Destroy target nonblack creature.
         */
        String sAssassin = "Stronghold Assassin";

        /*
            Graf Harvest {B}
            Enchantment
            Zombies you control have menace. (They can't be blocked except by two or more creatures.)
            {3}{B}, Exile a creature card from your graveyard: Create a 2/2 black Zombie creature token.
         */
        String gHarvest = "Graf Harvest";

        /*
            Erebos, God of the Dead {3}{B}
            Legendary Enchantment Creature — God 5/7
            Indestructible
            As long as your devotion to black is less than five, Erebos isn't a creature.
            Your opponents can't gain life.
            {1}{B}, Pay 2 life: Draw a card.
         */
        String erebos = "Erebos, God of the Dead";

        /*
            Phyrexian Obliterator {B}{B}{B}{B}
            Creature — Horror
            Trample
            Whenever a source deals damage to Phyrexian Obliterator, that source's controller sacrifices that many permanents.
         */
        String pObliterator = "Phyrexian Obliterator";

        addCard(Zone.BATTLEFIELD, playerA, nykthos);
        addCard(Zone.BATTLEFIELD, playerA, sAssassin);
        addCard(Zone.BATTLEFIELD, playerA, gHarvest);
        addCard(Zone.BATTLEFIELD, playerA, erebos);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 2); // two colorless to pay for nykthos
        addCard(Zone.HAND, playerA, pObliterator); // just for something to cast for 4 black mana

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}: Choose a color. Add an amount of mana of that color equal to your devotion to that color.");
        setChoice(playerA, "Black"); // should produce 4 black mana

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, pObliterator); // costs exactly 4 black mana should be castable

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTappedCount("Wastes", true, 2);
        assertTapped(nykthos, true);
        // Assert.assertEquals("amount of black mana", 4, playerA.getManaPool().getBlack()); // should be 4 black mana
        assertHandCount(playerA, pObliterator, 0);
        assertPermanentCount(playerA, pObliterator, 1);
    }
}
