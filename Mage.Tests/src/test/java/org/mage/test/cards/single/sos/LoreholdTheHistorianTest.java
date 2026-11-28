package org.mage.test.cards.single.sos;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class LoreholdTheHistorianTest extends CardTestPlayerBase {

    @Test
    public void test_FirstDrawSorcery_MiracleTwo() {
        skipInitShuffling();

        // Each instant and sorcery card in your hand has miracle {2}.
        addCard(Zone.BATTLEFIELD, playerA, "Lorehold, the Historian");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // Lava Axe deals 5 damage to target player or planeswalker.
        addCard(Zone.LIBRARY, playerA, "Lava Axe"); // {4}{R} -> miracle {2}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2); // pays miracle {2}

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 2); // pay Think Twice
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");
        setChoice(playerA, true); // reveal Lava Axe for miracle
        setChoice(playerA, true); // cast Lava Axe for miracle {2}
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Lava Axe", 1);
        assertLife(playerB, 20 - 5);
        assertTappedCount("Mountain", true, 2);
    }

    @Test
    public void test_OpponentsUpkeepDiscardDraw_TriggersMiracleOnOpponentsTurn() {
        // Lorehold, the Historian ruling:
        // You can reveal and cast a card with miracle on any turn, not just your own, if it's the first card you've drawn
        // that turn.
        // (2026-03-20)
        // Lorehold, the Historian ruling:
        // You cast the card with miracle during the resolution of the triggered ability. Ignore any timing rules based on
        // the card's type.
        // (2026-03-20)
        skipInitShuffling();

        // Each instant and sorcery card in your hand has miracle {2}.
        // At the beginning of each opponent's upkeep, you may discard a card. If you do, draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Lorehold, the Historian");
        addCard(Zone.HAND, playerA, "Plains"); // card to discard
        //
        // Lava Axe deals 5 damage to target player or planeswalker.
        addCard(Zone.LIBRARY, playerA, "Lava Axe"); // {4}{R} -> miracle {2}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2); // pays miracle {2}

        // Turn 2 (playerB's upkeep): Lorehold triggers -> discard Plains -> draw Lava Axe -> miracle {2}
        setChoice(playerA, true); // discard a card to draw a card? -> yes
        setChoice(playerA, "Plains"); // discard Plains
        setChoice(playerA, true); // reveal Lava Axe for miracle
        setChoice(playerA, true); // cast Lava Axe for miracle {2} (ignoring sorcery timing)
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Plains", 1);
        assertGraveyardCount(playerA, "Lava Axe", 1);
        assertLife(playerB, 20 - 5);
    }

    @Test
    public void test_FirstDrawCreature_NoMiracle() {
        skipInitShuffling();

        // Each instant and sorcery card in your hand has miracle {2}.
        addCard(Zone.BATTLEFIELD, playerA, "Lorehold, the Historian");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears"); // creature, not instant/sorcery
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        // No miracle dialog expected
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 0);
    }

    @Test
    public void test_FirstDrawAdventureCreature_NoMiracle() {
        skipInitShuffling();

        // Each instant and sorcery card in your hand has miracle {2}.
        addCard(Zone.BATTLEFIELD, playerA, "Lorehold, the Historian");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // Bonecrusher Giant (Creature) // Stomp (Instant -- Adventure).
        // In hand an adventurer card has only its normal characteristics (Creature), so it does not gain miracle.
        addCard(Zone.LIBRARY, playerA, "Bonecrusher Giant");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        // No miracle dialog expected
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Bonecrusher Giant", 1);
        assertLife(playerB, 20);
    }

    @Test
    public void test_FirstDrawSplitCard_ChooseHalfForMiracleTwo() {
        skipInitShuffling();

        // Each instant and sorcery card in your hand has miracle {2}.
        addCard(Zone.BATTLEFIELD, playerA, "Lorehold, the Historian");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // Fire {1}{R}: deals 2 damage divided among one or two targets. // Ice {1}{U}: Tap target permanent. Draw a card.
        addCard(Zone.LIBRARY, playerA, "Fire // Ice");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2); // pays generic miracle {2} (no red mana!)

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");
        setChoice(playerA, true); // reveal Fire // Ice for miracle
        setChoice(playerA, true); // cast for miracle {2}
        setChoice(playerA, "Cast Fire"); // choose Fire half
        addTargetAmount(playerA, playerB, 2);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Fire // Ice", 1);
        assertLife(playerB, 20 - 2);
        assertTappedCount("Plains", true, 2);
    }

    @Test
    public void test_FirstDrawFuseSplitCard_CastFusedForSingleMiracleTwo() {
        skipInitShuffling();

        // Each instant and sorcery card in your hand has miracle {2}.
        addCard(Zone.BATTLEFIELD, playerA, "Lorehold, the Historian");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // Wear {1}{R}: Destroy target artifact. // Tear {W}: Destroy target enchantment. Fuse.
        addCard(Zone.LIBRARY, playerA, "Wear // Tear");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2); // pays single miracle {2} for fused spell
        addCard(Zone.BATTLEFIELD, playerB, "Sol Ring"); // artifact target for Wear
        addCard(Zone.BATTLEFIELD, playerB, "Glorious Anthem"); // enchantment target for Tear

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");
        setChoice(playerA, true); // reveal Wear // Tear for miracle
        setChoice(playerA, true); // cast for miracle {2}
        setChoice(playerA, "Cast fused Wear // Tear"); // fuse both halves from hand for single {2}
        addTarget(playerA, "Sol Ring"); // Wear target
        addTarget(playerA, "Glorious Anthem"); // Tear target

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Wear // Tear", 1);
        assertGraveyardCount(playerB, "Sol Ring", 1);
        assertGraveyardCount(playerB, "Glorious Anthem", 1);
        assertTappedCount("Forest", true, 2);
    }

    @Ignore("TODO: PlayerImpl.getOtherUseableCastAbilities dead SPLIT_AFTERMATH branch offers Aftermath half from hand")
    @Test
    public void test_FirstDrawAftermathSplitCard_OnlyLeftHalfCastable() {
        skipInitShuffling();

        // Each instant and sorcery card in your hand has miracle {2}.
        addCard(Zone.BATTLEFIELD, playerA, "Lorehold, the Historian");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // Cut {1}{R} (Sorcery): Cut deals 4 damage to target creature.
        // Ribbons {X}{B}{B} (Sorcery, Aftermath: cast only from graveyard).
        addCard(Zone.LIBRARY, playerA, "Cut // Ribbons");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2); // pays miracle {2}
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");
        setChoice(playerA, true); // reveal Cut // Ribbons for miracle
        setChoice(playerA, true); // cast for miracle {2} (only Cut is legal from hand, no half choice)
        addTarget(playerA, "Grizzly Bears"); // Cut target

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Cut // Ribbons", 1);
        assertGraveyardCount(playerB, "Grizzly Bears", 1);
        assertTappedCount("Plains", true, 2);
    }

    @Test
    public void test_FirstDrawNoManaCostSorcery_CastForMiracleTwo() {
        skipInitShuffling();

        // Each instant and sorcery card in your hand has miracle {2}.
        addCard(Zone.BATTLEFIELD, playerA, "Lorehold, the Historian");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // Living End (no mana cost, Sorcery): Each player exiles all creature cards from their graveyard,
        // sacrifices all creatures they control, then puts exiled cards onto the battlefield.
        addCard(Zone.LIBRARY, playerA, "Living End");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2); // pays miracle {2}
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears"); // returned by Living End
        addCard(Zone.BATTLEFIELD, playerB, "Centaur Courser"); // sacrificed by Living End

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");
        setChoice(playerA, true); // reveal Living End for miracle
        setChoice(playerA, true); // cast Living End for miracle {2}

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Living End", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerB, "Centaur Courser", 1);
        assertTappedCount("Plains", true, 2);
    }

    @Test
    public void test_FirstDrawXSpell_XMustBeZero() {
        skipInitShuffling();

        // Each instant and sorcery card in your hand has miracle {2}.
        addCard(Zone.BATTLEFIELD, playerA, "Lorehold, the Historian");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // Fireball {X}{R}: deals X damage divided evenly among any number of targets.
        // Miracle {2} has no {X}, so X must be 0.
        addCard(Zone.LIBRARY, playerA, "Fireball");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5); // extra mana must NOT allow X > 0

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");
        setChoice(playerA, true); // reveal Fireball for miracle
        setChoice(playerA, true); // cast Fireball for miracle {2} (X must be 0, no X prompt)
        addTarget(playerA, playerB);
        addTarget(playerA, TestPlayer.TARGET_SKIP); // single target only

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Fireball", 1);
        assertLife(playerB, 20); // X = 0 -> 0 damage
        assertTappedCount("Mountain", true, 2);
    }

    @Test
    public void test_FirstDrawInstantWithKicker_PayKickerOnTopOfMiracle() {
        // Lorehold, the Historian ruling:
        // Miracle is an alternative cost to cast the spell with miracle. It can't be combined with other alternative costs,
        // such as casting a spell "without paying its mana cost." You may, however, pay additional costs, such as kicker.
        // If the spell has any mandatory additional costs, those must be paid to cast the spell.
        // (2026-03-20)
        skipInitShuffling();

        // Each instant and sorcery card in your hand has miracle {2}.
        addCard(Zone.BATTLEFIELD, playerA, "Lorehold, the Historian");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // Into the Roil {1}{U}, Kicker {1}{U}: Return target nonland permanent to its owner's hand. If kicked, draw a card.
        addCard(Zone.LIBRARY, playerA, "Plains"); // drawn if kicked
        addCard(Zone.LIBRARY, playerA, "Into the Roil"); // top of library -> miracle {2} + kicker {1}{U} = {3}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4); // pays {3}{U}
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 2); // pay Think Twice
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");
        setChoice(playerA, true); // reveal Into the Roil for miracle
        setChoice(playerA, true); // cast for miracle {2}
        setChoice(playerA, true); // pay Kicker {1}{U}
        addTarget(playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Into the Roil", 1);
        assertHandCount(playerB, "Grizzly Bears", 1);
        assertHandCount(playerA, "Plains", 1); // drawn from kicker
        assertTappedCount("Island", true, 2 + 4);
    }

    @Test
    public void test_FirstDrawMDFCSorceryLand_CastSpellSideOnly() {
        skipInitShuffling();

        // Each instant and sorcery card in your hand has miracle {2}.
        addCard(Zone.BATTLEFIELD, playerA, "Lorehold, the Historian");
        // Draw a card.
        addCard(Zone.HAND, playerA, "Think Twice"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // Bala Ged Recovery {2}{G} (Sorcery: Return target card from your graveyard to your hand) // Bala Ged Sanctuary (Land)
        addCard(Zone.LIBRARY, playerA, "Bala Ged Recovery");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2); // pays miracle {2}
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears"); // target to return

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Think Twice");
        setChoice(playerA, true); // reveal Bala Ged Recovery for miracle
        setChoice(playerA, true); // cast Bala Ged Recovery for miracle {2}
        addTarget(playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Bala Ged Recovery", 1);
        assertHandCount(playerA, "Grizzly Bears", 1);
        assertTappedCount("Plains", true, 2);
    }
}
