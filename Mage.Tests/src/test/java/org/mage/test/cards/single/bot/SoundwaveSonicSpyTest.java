package org.mage.test.cards.single.bot;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 701.28a
 * To convert a permanent, turn it so that its other face is up. This follows rules 701.27a–f, 712.9–10, and 712.18.
 * Those rules apply to converting a permanent just as they apply to transforming a permanent.
 * <p>
 * 701.28e
 * If an activated or triggered ability of a permanent that isn’t a delayed triggered ability of that permanent tries
 * to convert it, the permanent does so only if it hasn’t converted or transformed since the ability was put onto the
 * stack. If a delayed triggered ability of a permanent tries to convert that permanent, the permanent does so only if
 * it hasn’t converted or transformed since that delayed triggered ability was created. In both cases, if the permanent
 * has already transformed or converted, an instruction to do either is ignored.
 *
 * @author DarioBarrabinoDev
 */
public class SoundwaveSonicSpyTest extends CardTestPlayerBase {

    @Test
    public void testCastCopyConverts() {
        // Whenever one or more creature tokens you control deal combat damage to a player, exile target instant or
        // sorcery card with mana value equal to the damage dealt from their graveyard. Copy it. You may cast the copy
        // without paying its mana cost. If you do, convert Soundwave.
        addCard(Zone.BATTLEFIELD, playerA, "Soundwave, Sonic Spy"); // 5/4
        addCard(Zone.HAND, playerA, "Raise the Alarm"); // {1}{W} instant: create two 1/1 white Soldier creature tokens
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        //
        addCard(Zone.GRAVEYARD, playerB, "Lightning Bolt"); // {R} instant, mana value 1
        addCard(Zone.GRAVEYARD, playerB, "Night's Whisper"); // {1}{B} sorcery, mana value 2: you draw two cards and you lose 2 life

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raise the Alarm");

        // Soundwave isn't a token, so only the two 1/1 Soldier tokens count for the damage dealt
        attack(3, playerA, "Soundwave, Sonic Spy", playerB);
        attack(3, playerA, "Soldier Token", playerB);
        attack(3, playerA, "Soldier Token", playerB);
        addTarget(playerA, "Night's Whisper"); // mana value 2 = damage dealt by both tokens
        setChoice(playerA, true); // cast the copy

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20 - 5 - 1 - 1);
        assertLife(playerA, 20 - 2);
        assertHandCount(playerA, 1 + 2); // turn 3 draw + copy of Night's Whisper
        assertExileCount(playerB, "Night's Whisper", 1);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertPermanentCount(playerA, "Soundwave, Superior Captain", 1);
        assertPermanentCount(playerA, "Soundwave, Sonic Spy", 0);
    }

    @Test
    public void testDeclineCopyDoesNotConvert() {
        // Whenever one or more creature tokens you control deal combat damage to a player, exile target instant or
        // sorcery card with mana value equal to the damage dealt from their graveyard. Copy it. You may cast the copy
        // without paying its mana cost. If you do, convert Soundwave.
        addCard(Zone.BATTLEFIELD, playerA, "Soundwave, Sonic Spy"); // 5/4
        addCard(Zone.HAND, playerA, "Raise the Alarm"); // {1}{W} instant: create two 1/1 white Soldier creature tokens
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        //
        addCard(Zone.GRAVEYARD, playerB, "Lightning Bolt"); // {R} instant, mana value 1
        addCard(Zone.GRAVEYARD, playerB, "Night's Whisper"); // {1}{B} sorcery, mana value 2: you draw two cards and you lose 2 life

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raise the Alarm");

        // Soundwave isn't a token, so only the two 1/1 Soldier tokens count for the damage dealt
        attack(3, playerA, "Soundwave, Sonic Spy", playerB);
        attack(3, playerA, "Soldier Token", playerB);
        attack(3, playerA, "Soldier Token", playerB);
        addTarget(playerA, "Night's Whisper"); // mana value 2 = damage dealt by both tokens
        setChoice(playerA, false); // don't cast the copy

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20 - 5 - 1 - 1);
        assertLife(playerA, 20);
        assertHandCount(playerA, 1); // turn 3 draw only
        assertExileCount(playerB, "Night's Whisper", 1);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertPermanentCount(playerA, "Soundwave, Sonic Spy", 1);
        assertPermanentCount(playerA, "Soundwave, Superior Captain", 0);
    }

    @Test
    public void testNoCardWithManaValueEqualToDamage() {
        // Whenever one or more creature tokens you control deal combat damage to a player, exile target instant or
        // sorcery card with mana value equal to the damage dealt from their graveyard. Copy it. You may cast the copy
        // without paying its mana cost. If you do, convert Soundwave.
        addCard(Zone.BATTLEFIELD, playerA, "Soundwave, Sonic Spy"); // 5/4
        addCard(Zone.HAND, playerA, "Raise the Alarm"); // {1}{W} instant: create two 1/1 white Soldier creature tokens
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        //
        addCard(Zone.GRAVEYARD, playerB, "Lightning Bolt"); // {R} instant, mana value 1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raise the Alarm");

        // both tokens deal 2 damage, so the trigger has no legal target and does nothing
        attack(3, playerA, "Soldier Token", playerB);
        attack(3, playerA, "Soldier Token", playerB);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20 - 1 - 1);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertExileCount(playerB, "Lightning Bolt", 0);
        assertPermanentCount(playerA, "Soundwave, Sonic Spy", 1);
        assertPermanentCount(playerA, "Soundwave, Superior Captain", 0);
    }

    @Test
    public void testOnlyDamagedPlayerGraveyard() {
        // Whenever one or more creature tokens you control deal combat damage to a player, exile target instant or
        // sorcery card with mana value equal to the damage dealt from their graveyard. Copy it. You may cast the copy
        // without paying its mana cost. If you do, convert Soundwave.
        addCard(Zone.BATTLEFIELD, playerA, "Soundwave, Sonic Spy"); // 5/4
        addCard(Zone.HAND, playerA, "Raise the Alarm"); // {1}{W} instant: create two 1/1 white Soldier creature tokens
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.GRAVEYARD, playerA, "Night's Whisper"); // {1}{B} sorcery, mana value 2, but in your own graveyard

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raise the Alarm");

        // both tokens deal 2 damage to playerB, whose graveyard is empty, so the trigger has no legal target
        attack(3, playerA, "Soldier Token", playerB);
        attack(3, playerA, "Soldier Token", playerB);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20 - 1 - 1);
        assertGraveyardCount(playerA, "Night's Whisper", 1);
        assertExileCount(playerA, "Night's Whisper", 0);
        assertPermanentCount(playerA, "Soundwave, Sonic Spy", 1);
        assertPermanentCount(playerA, "Soundwave, Superior Captain", 0);
    }

    @Test
    public void testOddManaValueCreatesRavage() {
        // More Than Meets the Eye {2}{W}{U}{B} (You may cast this card converted for {2}{W}{U}{B}.)
        // Whenever you cast a spell with an odd mana value, convert Soundwave. If you do, create Ravage, a legendary
        // 3/3 black Robot artifact creature token with menace and deathtouch.
        addCard(Zone.HAND, playerA, "Soundwave, Sonic Spy");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R} instant, mana value 1: 3 damage to any target
        addCard(Zone.HAND, playerA, "Night's Whisper"); // {1}{B} sorcery, mana value 2: you draw two cards and you lose 2 life

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Soundwave, Superior Captain using More than Meets the Eye");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("converted", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Soundwave, Superior Captain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Soundwave is a creature again, so this spell doesn't trigger
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Night's Whisper");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 3);
        assertPermanentCount(playerA, "Soundwave, Sonic Spy", 1);
        assertPermanentCount(playerA, "Soundwave, Superior Captain", 0);
        assertPermanentCount(playerA, "Ravage", 1);
        assertPermanentCount(playerA, "Laserbeak", 0);
        assertPowerToughness(playerA, "Ravage", 3, 3);
    }

    @Test
    public void testEvenManaValueCreatesLaserbeak() {
        // More Than Meets the Eye {2}{W}{U}{B} (You may cast this card converted for {2}{W}{U}{B}.)
        // Whenever you cast a spell with an even mana value, convert Soundwave. If you do, create Laserbeak, a
        // legendary 2/2 blue Robot artifact creature token with flying and hexproof.
        addCard(Zone.HAND, playerA, "Soundwave, Sonic Spy");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        //
        addCard(Zone.HAND, playerA, "Night's Whisper"); // {1}{B} sorcery, mana value 2: you draw two cards and you lose 2 life

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Soundwave, Superior Captain using More than Meets the Eye");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("converted", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Soundwave, Superior Captain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Night's Whisper");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 - 2);
        assertPermanentCount(playerA, "Soundwave, Sonic Spy", 1);
        assertPermanentCount(playerA, "Soundwave, Superior Captain", 0);
        assertPermanentCount(playerA, "Laserbeak", 1);
        assertPermanentCount(playerA, "Ravage", 0);
        assertPowerToughness(playerA, "Laserbeak", 2, 2);
    }

    @Test
    public void testTwoTriggersOnStackOnlyFirstConverts() {
        // Whenever you cast a spell with an odd mana value, convert Soundwave. If you do, create Ravage, a legendary
        // 3/3 black Robot artifact creature token with menace and deathtouch.
        // Whenever you cast a spell with an even mana value, convert Soundwave. If you do, create Laserbeak, a
        // legendary 2/2 blue Robot artifact creature token with flying and hexproof.
        addCard(Zone.HAND, playerA, "Soundwave, Sonic Spy");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R} instant, mana value 1: 3 damage to any target
        addCard(Zone.HAND, playerA, "Searing Spear"); // {1}{R} instant, mana value 2: 3 damage to any target

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Soundwave, Superior Captain using More than Meets the Eye");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("converted", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Soundwave, Superior Captain", 1);

        // cast both spells while holding priority, so both triggers are on the stack at the same time
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Searing Spear", playerB);
        checkStackSize("both spells and both triggers", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 4);

        // the even trigger resolves first and converts Soundwave, then the odd trigger is ignored (701.28e)

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 3 - 3);
        assertPermanentCount(playerA, "Soundwave, Sonic Spy", 1);
        assertPermanentCount(playerA, "Soundwave, Superior Captain", 0);
        assertPermanentCount(playerA, "Laserbeak", 1);
        assertPermanentCount(playerA, "Ravage", 0);
    }
}
