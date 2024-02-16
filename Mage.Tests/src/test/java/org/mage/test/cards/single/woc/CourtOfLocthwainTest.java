package org.mage.test.cards.single.woc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class CourtOfLocthwainTest extends CardTestPlayerBase {

    /**
     * Court of Locthwain
     * {2}{B}{B}
     * Enchantment
     *
     * When Court of Locthwain enters the battlefield, you become the monarch.
     *
     * At the beginning of your upkeep, exile the top card of target opponent's library. You may play that card for as long as it remains exiled, and mana of any type can be spent to cast it. If you're the monarch, until end of turn, you may cast a spell from among cards exiled with Court of Locthwain without paying its mana cost.
     */
    private static String court = "Court of Locthwain";

    /**
     * Armageddon
     * {3}{W}
     * Sorcery
     *
     * Destroy all lands.
     */
    private static String armageddon = "Armageddon";

    private static String evangel = "Cabal Evangel"; // 2/2
    private static String reveler = "Falkenrath Reaver"; // 2/2

    @Test
    public void testNoMonarch() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, court);
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 4);
        addCard(Zone.BATTLEFIELD, playerB, evangel);
        addCard(Zone.LIBRARY, playerB, reveler);
        addCard(Zone.LIBRARY, playerB, "Island", 2); // playerB will draw those.
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, court);
        attack(2, playerB, evangel, playerA); // B takes the monarch
        addTarget(playerA, playerB); // trigger target.

        setStopAt(3, PhaseStep.DRAW);
        execute();

        assertExileCount(reveler, 1);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, reveler);

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, reveler, 1);
        assertTappedCount("Scrubland", true, 2);
    }

    @Test
    public void testMonarchChoiceCastForFree() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, court);
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 4);
        addCard(Zone.LIBRARY, playerB, reveler);
        addCard(Zone.LIBRARY, playerB, "Island"); // playerB will draw it.
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, court);

        addTarget(playerA, playerB); // trigger target for turn 3
        checkExileCount("reveler got exiled", 3, PhaseStep.PRECOMBAT_MAIN, playerA, reveler, 1);

        // We need to choose the proper AsThough, even if only one is valid.
        setChoice(playerA, "Without paying manacost: ");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, reveler);

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, reveler, 1);
        assertTappedCount("Scrubland", true, 0);
    }

    @Test
    public void testMonarchChoiceCastForMana() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, court);
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 4);
        addCard(Zone.LIBRARY, playerB, reveler);
        addCard(Zone.LIBRARY, playerB, "Island"); // playerB will draw it.
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, court);

        addTarget(playerA, playerB); // trigger target for turn 3
        checkExileCount("reveler got exiled", 3, PhaseStep.PRECOMBAT_MAIN, playerA, reveler, 1);

        // We need to choose the proper AsThough, even if only one is valid.
        setChoice(playerA, "Court");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, reveler);

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, reveler, 1);
        assertTappedCount("Scrubland", true, 2);
    }

    @Test
    public void testMonarchArmageddon() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, court);
        addCard(Zone.HAND, playerA, armageddon);
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 8);

        addCard(Zone.LIBRARY, playerB, reveler); // will be exiled by Court
        addCard(Zone.LIBRARY, playerB, "Island"); // playerB will draw it.
        addCard(Zone.LIBRARY, playerB, evangel); // will be exiled by Court
        addCard(Zone.LIBRARY, playerB, "Island"); // playerB will draw it.
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, court, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, armageddon);

        addTarget(playerA, playerB); // trigger target for turn 3
        checkExileCount("evangel got exiled", 3, PhaseStep.PRECOMBAT_MAIN, playerA, evangel, 1);
        checkExileCount("reveler is not yet exiled", 3, PhaseStep.PRECOMBAT_MAIN, playerA, reveler, 0);

        addTarget(playerA, playerB); // trigger target for turn 5.
        checkExileCount("evangel still exiled", 5, PhaseStep.PRECOMBAT_MAIN, playerA, evangel, 1);
        checkExileCount("reveler got exiled", 5, PhaseStep.PRECOMBAT_MAIN, playerA, reveler, 1);

        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, reveler, true);
        setChoice(playerA, "Without paying manacost");

        setStopAt(5, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, reveler, 1);
        assertPermanentCount(playerA, "Scrubland", 0);
    }

    @Test
    public void testMonarchDoubleCast() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, court);
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 4);

        addCard(Zone.LIBRARY, playerB, reveler); // will be exiled by Court
        addCard(Zone.LIBRARY, playerB, "Island"); // playerB will draw it.
        addCard(Zone.LIBRARY, playerB, evangel); // will be exiled by Court
        addCard(Zone.LIBRARY, playerB, "Island"); // playerB will draw it.
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, court);

        addTarget(playerA, playerB); // trigger target for turn 3
        checkExileCount("evangel got exiled", 3, PhaseStep.PRECOMBAT_MAIN, playerA, evangel, 1);
        checkExileCount("reveler is not yet exiled", 3, PhaseStep.PRECOMBAT_MAIN, playerA, reveler, 0);

        addTarget(playerA, playerB); // trigger target for turn 5.
        checkExileCount("evangel still exiled", 5, PhaseStep.PRECOMBAT_MAIN, playerA, evangel, 1);
        checkExileCount("reveler got exiled", 5, PhaseStep.PRECOMBAT_MAIN, playerA, reveler, 1);

        setChoice(playerA, "Court");
        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, evangel, true);

        setChoice(playerA, "Without paying manacost");
        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, reveler);

        setStopAt(5, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, evangel, 1);
        assertPermanentCount(playerA, reveler, 1);
        assertTappedCount("Scrubland", true, 2);
    }
}
