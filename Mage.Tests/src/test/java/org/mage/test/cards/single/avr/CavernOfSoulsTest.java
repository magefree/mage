package org.mage.test.cards.single.avr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 */
public class CavernOfSoulsTest extends CardTestPlayerBase {

    /**
     * Tests simple cast
     */
    @Test
    public void testCastDrake() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, "Cavern of Souls");
        addCard(Zone.HAND, playerA, "Azure Drake");

        setChoice(playerA, "Drake");
        setChoice(playerA, "Blue");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cavern of Souls");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Azure Drake");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Azure Drake", 1);
    }

    /**
     * Tests "Cavern of Souls" with "Human" creature type chosen. Then tests
     * casting Azure Drake (should fail) and Elite Vanguard (should be ok as it
     * has "Human" subtype)
     */
    @Test
    public void testNoCastBecauseOfCreatureType() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Cavern of Souls");
        addCard(Zone.HAND, playerA, "Abuna Acolyte");
        addCard(Zone.HAND, playerA, "Elite Vanguard");

        setChoice(playerA, "Human");
        setChoice(playerA, "White");
        setChoice(playerA, "White");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cavern of Souls"); // choose Human
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Abuna Acolyte");  // not Human but Cat Cleric
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elite Vanguard"); // Human

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Abuna Acolyte", 0);
        assertPermanentCount(playerA, "Elite Vanguard", 1);
    }

    /**
     * Tests card can be countered for usual cast
     */
    @Test
    public void testDrakeCountered() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, "Island");
        addCard(Zone.HAND, playerA, "Azure Drake");

        addCard(Zone.HAND, playerB, "Remove Soul");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Island");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Azure Drake");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Remove Soul");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Azure Drake", 0);
        assertGraveyardCount(playerA, "Azure Drake", 1);
    }

    /**
     * Tests spell can't be countered for cast with Cavern of Souls
     */
    @Test
    public void testDrakeCantBeCountered() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        // As Cavern of Souls enters the battlefield, choose a creature type.
        // {T}: Add {C}.
        // {T}: Add one mana of any color. Spend this mana only to cast a creature spell of the chosen type, and that spell can't be countered.
        addCard(Zone.HAND, playerA, "Cavern of Souls");
        addCard(Zone.HAND, playerA, "Azure Drake");

        // {1}{U} Remove Soul - Counter target creature spell.
        addCard(Zone.HAND, playerB, "Remove Soul");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        setChoice(playerA, "Drake");
        setChoice(playerA, "Blue");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cavern of Souls");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Azure Drake");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Remove Soul");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // check wasn't countered
        assertGraveyardCount(playerA, "Azure Drake", 0);
        assertPermanentCount(playerA, "Azure Drake", 1);
    }

    /**
     * Tests spell can be countered if cast with colorless mana from Cavern
     */
    @Test
    public void testDrakeCanBeCountered() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.HAND, playerA, "Cavern of Souls");
        addCard(Zone.HAND, playerA, "Azure Drake");

        // {1}{U} Remove Soul - Counter target creature spell.
        addCard(Zone.HAND, playerB, "Remove Soul");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        setChoice(playerA, "Drake");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cavern of Souls");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Azure Drake");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Remove Soul");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // check it was countered
        assertGraveyardCount(playerB, "Remove Soul", 1);
        assertGraveyardCount(playerA, "Azure Drake", 1);
        assertPermanentCount(playerA, "Azure Drake", 0);
    }

    /**
     * Tests conditional mana from Cavern in pool will still work if Cavern got
     * back to hand and is played again with other creature type
     */
    @Test
    public void testConditionlManaWorksIfCavernIsReplayed() {
        addCard(Zone.HAND, playerA, "Cavern of Souls");
        addCard(Zone.HAND, playerA, "Gladecover Scout"); // Elf costing {G}
        // addCard(Zone.HAND, playerA, "Fume Spitter"); // Horror costing {B}

        // Instant - {U}{U} - Return target permanent to its owner's hand.
        addCard(Zone.HAND, playerB, "Boomerang");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cavern of Souls");
        setChoice(playerA, "Elf");

        // getting green mana for Elf into pool
        activateManaAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add 1 mana of any one color. Spend this mana only to cast a creature spell of the chosen type, and that spell can't be countered.");
        setChoice(playerA, "Green");

        // return cavern to hand
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerB, "Boomerang", "Cavern of Souls");

        // playing the cavern again choose different creature type
        playLand(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cavern of Souls");
        setChoice(playerA, "Horror");

        // the green mana usable for Elf should be in the mana pool
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Gladecover Scout");

        activateManaAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add 1 mana of any one color. Spend this mana only to cast a creature spell of the chosen type, and that spell can't be countered.");
        setChoice(playerA, "Black");

        // the black mana usable for Horror should be in the mana pool
        // castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Fume Spitter");
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Boomerang", 1);
        assertPermanentCount(playerA, "Cavern of Souls", 1);

        // Check the elf was cast
        assertPermanentCount(playerA, "Gladecover Scout", 1);
        // Check Horror on the Battlefield
        // assertPermanentCount(playerA, "Fume Spitter", 1);
    }

    /**
     * Return to the Ranks cannot be countered if mana produced by Cavern of
     * Souls was used to pay X. Can be bug also for all other spells with X in
     * their cost, not sure.
     *
     */
    @Test
    public void testCastWithColorlessManaCanBeCountered() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Cavern of Souls");
        // Sorcery {X}{W}{W}
        // Convoke (Your creatures can help cast this spell. Each creature you tap while casting this spell pays for or one mana of that creature's color.)
        // Return X target creature cards with converted mana cost 2 or less from your graveyard to the battlefield.
        addCard(Zone.HAND, playerA, "Return to the Ranks");
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion");

        // {1}{U} Remove Soul - Counter target creature spell.
        addCard(Zone.HAND, playerB, "Counterspell");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cavern of Souls");
        setChoice(playerA, "Drake");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Return to the Ranks", "Silvercoat Lion");
        setChoice(playerA, "X=1");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Counterspell", "Return to the Ranks");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // check it was countered
        assertGraveyardCount(playerA, "Return to the Ranks", 1);
        assertGraveyardCount(playerB, "Counterspell", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 0);

    }

    /**
     * Cavern of Souls can produce any colour of mana with its second ability
     * when Contamination is in play.
     */
    @Test
    public void testUseWithConversionInPlay() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.HAND, playerA, "Cavern of Souls");
        // Creature - Drake {3}{R}
        addCard(Zone.HAND, playerA, "Desert Drake");

        addCard(Zone.BATTLEFIELD, playerB, "Contamination", 1);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cavern of Souls");
        setChoice(playerA, "Drake");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Desert Drake");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Cavern of Souls", 1);
        assertPermanentCount(playerA, "Desert Drake", 0);

    }

}
