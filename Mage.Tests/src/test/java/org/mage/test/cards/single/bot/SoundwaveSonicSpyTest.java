package org.mage.test.cards.single.bot;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DarioBarrabinoDev
 */
public class SoundwaveSonicSpyTest extends CardTestPlayerBase {

    /*
     * Soundwave, Sonic Spy {1}{W}{U}{B}
     * Legendary Artifact Creature — Robot
     * More Than Meets the Eye {2}{W}{U}{B} (You may cast this card converted for {2}{W}{U}{B}.)
     * Whenever one or more creature tokens you control deal combat damage to a player, exile target instant or sorcery
     * card with mana value equal to the damage dealt from their graveyard. Copy it. You may cast the copy without paying
     * its mana cost. If you do, convert Soundwave.
     * 5/4
     *
     * Soundwave, Superior Captain
     * Legendary Artifact
     * Whenever you cast a spell with an odd mana value, convert Soundwave. If you do, create Ravage, a legendary 3/3
     * black Robot artifact creature token with menace and deathtouch.
     * Whenever you cast a spell with an even mana value, convert Soundwave. If you do, create Laserbeak, a legendary 2/2
     * blue Robot artifact creature token with flying and hexproof.
     */
    private static final String soundwave = "Soundwave, Sonic Spy";
    private static final String captain = "Soundwave, Superior Captain";

    private static final String alarm = "Raise the Alarm"; // {1}{W} instant: create two 1/1 white Soldier creature tokens
    private static final String bolt = "Lightning Bolt"; // {R} instant, mana value 1
    private static final String whisper = "Night's Whisper"; // {1}{B} sorcery, mana value 2: you draw two cards and you lose 2 life

    private void setupCombat() {
        addCard(Zone.BATTLEFIELD, playerA, soundwave);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, alarm);
        addCard(Zone.GRAVEYARD, playerB, bolt);
        addCard(Zone.GRAVEYARD, playerB, whisper);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, alarm);

        // Soundwave isn't a token, so only the two 1/1 Soldier tokens count for the damage dealt
        attack(3, playerA, soundwave, playerB);
        attack(3, playerA, "Soldier Token", playerB);
        attack(3, playerA, "Soldier Token", playerB);
        addTarget(playerA, whisper); // mana value 2 = damage dealt by both tokens
    }

    @Test
    public void testCastCopyConverts() {
        setupCombat();
        setChoice(playerA, true); // cast the copy

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20 - 5 - 1 - 1);
        assertLife(playerA, 20 - 2);
        assertHandCount(playerA, 1 + 2); // turn 3 draw + copy of Night's Whisper
        assertExileCount(playerB, whisper, 1);
        assertGraveyardCount(playerB, bolt, 1);
        assertPermanentCount(playerA, captain, 1);
        assertPermanentCount(playerA, soundwave, 0);
    }

    @Test
    public void testDeclineCopyDoesNotConvert() {
        setupCombat();
        setChoice(playerA, false); // don't cast the copy

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20 - 5 - 1 - 1);
        assertLife(playerA, 20);
        assertHandCount(playerA, 1);
        assertExileCount(playerB, whisper, 1);
        assertGraveyardCount(playerB, bolt, 1);
        assertPermanentCount(playerA, soundwave, 1);
        assertPermanentCount(playerA, captain, 0);
    }

    @Test
    public void testOddManaValueCreatesRavage() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, soundwave);
        addCard(Zone.HAND, playerA, bolt);
        addCard(Zone.HAND, playerA, whisper);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + captain + " using More than Meets the Eye");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("converted", 1, PhaseStep.PRECOMBAT_MAIN, playerA, captain, 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Soundwave is a creature again, so this spell doesn't trigger
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, whisper);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 3);
        assertPermanentCount(playerA, soundwave, 1);
        assertPermanentCount(playerA, captain, 0);
        assertPermanentCount(playerA, "Ravage", 1);
        assertPermanentCount(playerA, "Laserbeak", 0);
        assertPowerToughness(playerA, "Ravage", 3, 3);
    }

    @Test
    public void testEvenManaValueCreatesLaserbeak() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, soundwave);
        addCard(Zone.HAND, playerA, whisper);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + captain + " using More than Meets the Eye");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("converted", 1, PhaseStep.PRECOMBAT_MAIN, playerA, captain, 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, whisper);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 - 2);
        assertPermanentCount(playerA, soundwave, 1);
        assertPermanentCount(playerA, captain, 0);
        assertPermanentCount(playerA, "Laserbeak", 1);
        assertPermanentCount(playerA, "Ravage", 0);
        assertPowerToughness(playerA, "Laserbeak", 2, 2);
    }
}
