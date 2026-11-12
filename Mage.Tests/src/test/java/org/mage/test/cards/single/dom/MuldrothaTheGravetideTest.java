package org.mage.test.cards.single.dom;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 305.9
 * If an object is both a land and another card type, it can be played only as a land. It can't be cast as a spell.
 * <p>
 * Ruling (2020-11-10): Use the type of the card as it's played or cast to determine which permanent type to count it as.
 *
 * @author DarioBarrabinoDev
 */
public class MuldrothaTheGravetideTest extends CardTestPlayerBase {

    @Test
    public void test_LandThenPermanent() {
        // During each of your turns, you may play a land and cast a permanent spell of each permanent type from
        // your graveyard. (If a card has multiple permanent types, choose one as you play it.)
        addCard(Zone.BATTLEFIELD, playerA, "Muldrotha, the Gravetide");
        // You may play an additional land on each of your turns.
        addCard(Zone.BATTLEFIELD, playerA, "Exploration");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        //
        addCard(Zone.GRAVEYARD, playerA, "Mountain");
        addCard(Zone.GRAVEYARD, playerA, "Swamp");
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears"); // {1}{G} creature 2/2
        addCard(Zone.HAND, playerA, "Island");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mountain");
        checkPlayableAbility("land is used", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Swamp", false);
        checkPlayableAbility("second land play is free", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Island", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Mountain", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerA, "Swamp", 1);
    }

    @Test
    public void test_ArtifactCreature_ThenArtifactOrCreature() {
        // During each of your turns, you may play a land and cast a permanent spell of each permanent type from
        // your graveyard. (If a card has multiple permanent types, choose one as you play it.)
        addCard(Zone.BATTLEFIELD, playerA, "Muldrotha, the Gravetide");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        //
        addCard(Zone.GRAVEYARD, playerA, "Ornithopter"); // {0} artifact creature 0/2
        addCard(Zone.GRAVEYARD, playerA, "Mox Jet"); // {0} artifact
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears"); // {1}{G} creature 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ornithopter"); // no type choice
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("artifact is free", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Mox Jet", true);
        checkPlayableAbility("creature is free", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Grizzly Bears", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mox Jet"); // Ornithopter counts as creature now
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("artifact and creature are used", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Grizzly Bears", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Ornithopter", 1);
        assertPermanentCount(playerA, "Mox Jet", 1);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void test_DryadArbor_PlayedAsLand() {
        // During each of your turns, you may play a land and cast a permanent spell of each permanent type from
        // your graveyard. (If a card has multiple permanent types, choose one as you play it.)
        addCard(Zone.BATTLEFIELD, playerA, "Muldrotha, the Gravetide");
        // You may play an additional land on each of your turns.
        addCard(Zone.BATTLEFIELD, playerA, "Exploration");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        //
        addCard(Zone.GRAVEYARD, playerA, "Dryad Arbor"); // land creature 1/1
        addCard(Zone.GRAVEYARD, playerA, "Swamp");
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears"); // {1}{G} creature 2/2

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dryad Arbor"); // no type choice
        checkPlayableAbility("land is used", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Swamp", false);
        checkPlayableAbility("creature is free", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Grizzly Bears", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Dryad Arbor", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerA, "Swamp", 1);
    }

    @Test
    public void test_DryadArbor_CantBePlayedAsCreature() {
        // During each of your turns, you may play a land and cast a permanent spell of each permanent type from
        // your graveyard. (If a card has multiple permanent types, choose one as you play it.)
        addCard(Zone.BATTLEFIELD, playerA, "Muldrotha, the Gravetide");
        // You may play an additional land on each of your turns.
        addCard(Zone.BATTLEFIELD, playerA, "Exploration");
        //
        addCard(Zone.GRAVEYARD, playerA, "Swamp");
        addCard(Zone.GRAVEYARD, playerA, "Dryad Arbor"); // land creature 1/1

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Swamp");
        checkPlayableAbility("land is used, creature can't be used by a land", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Dryad Arbor", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Swamp", 1);
        assertGraveyardCount(playerA, "Dryad Arbor", 1);
    }

    @Test
    public void test_NewMuldrotha_NewUses() {
        // During each of your turns, you may play a land and cast a permanent spell of each permanent type from
        // your graveyard. (If a card has multiple permanent types, choose one as you play it.)
        addCard(Zone.BATTLEFIELD, playerA, "Muldrotha, the Gravetide");
        addCard(Zone.HAND, playerA, "Muldrotha, the Gravetide"); // {3}{B}{G}{U}
        // The "legend rule" doesn't apply.
        addCard(Zone.BATTLEFIELD, playerA, "Mirror Gallery");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears"); // {1}{G} creature 2/2
        addCard(Zone.GRAVEYARD, playerA, "Balduvian Bears"); // {1}{G} creature 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("creature is used", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Balduvian Bears", false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Muldrotha, the Gravetide");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("creature is free for new Muldrotha", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Balduvian Bears", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Muldrotha, the Gravetide", 2);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Balduvian Bears", 1);
    }

    @Test
    public void test_SameCardBackInGraveyard() {
        // During each of your turns, you may play a land and cast a permanent spell of each permanent type from
        // your graveyard. (If a card has multiple permanent types, choose one as you play it.)
        addCard(Zone.BATTLEFIELD, playerA, "Muldrotha, the Gravetide");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        //
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears"); // {1}{G} creature 2/2
        // Lightning Bolt deals 3 damage to any target.
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("creature is used", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Grizzly Bears", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
    }

    @Test
    public void test_SameCardBackInGraveyard_OtherTypeIsFree() {
        // During each of your turns, you may play a land and cast a permanent spell of each permanent type from
        // your graveyard. (If a card has multiple permanent types, choose one as you play it.)
        addCard(Zone.BATTLEFIELD, playerA, "Muldrotha, the Gravetide");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        //
        addCard(Zone.GRAVEYARD, playerA, "Ornithopter"); // {0} artifact creature 0/2
        addCard(Zone.GRAVEYARD, playerA, "Mox Jet"); // {0} artifact
        // Lightning Bolt deals 3 damage to any target.
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ornithopter"); // as artifact or creature
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Ornithopter");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("new object can use the other type", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Ornithopter", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ornithopter"); // as the other type
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("artifact and creature are used", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Mox Jet", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Ornithopter", 1);
        assertGraveyardCount(playerA, "Mox Jet", 1);
    }

    @Test
    public void test_CastCardGainsType_MustNotFreeUsedType() {
        // During each of your turns, you may play a land and cast a permanent spell of each permanent type from
        // your graveyard. (If a card has multiple permanent types, choose one as you play it.)
        addCard(Zone.BATTLEFIELD, playerA, "Muldrotha, the Gravetide");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // creature 2/2, to crew
        //
        // Crew 1 (Tap any number of creatures you control with total power 1 or more: This Vehicle becomes an
        // artifact creature until end of turn.)
        addCard(Zone.GRAVEYARD, playerA, "Smuggler's Copter"); // {2} artifact
        addCard(Zone.GRAVEYARD, playerA, "Sol Ring"); // {1} artifact

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Smuggler's Copter"); // as artifact
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("artifact is used", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Sol Ring", false);

        // Smuggler's Copter becomes an artifact creature, but it was cast as artifact only
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crew 1");
        setChoice(playerA, "Silvercoat Lion"); // crew cost
        setChoice(playerA, TestPlayer.CHOICE_SKIP); // don't tap Muldrotha too
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("artifact is still used after crew", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Sol Ring", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertType("Smuggler's Copter", CardType.CREATURE, true);
        assertTapped("Silvercoat Lion", true);
        assertGraveyardCount(playerA, "Sol Ring", 1);
    }

    @Test
    public void test_CastCardLosesType_MustNotBlockOtherType() {
        // During each of your turns, you may play a land and cast a permanent spell of each permanent type from
        // your graveyard. (If a card has multiple permanent types, choose one as you play it.)
        addCard(Zone.BATTLEFIELD, playerA, "Muldrotha, the Gravetide");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        //
        // As long as your devotion to white is less than five, Heliod, God of the Sun isn't a creature.
        addCard(Zone.GRAVEYARD, playerA, "Heliod, God of the Sun"); // enchantment creature {3}{W}
        // Creatures you control get +1/+1.
        addCard(Zone.GRAVEYARD, playerA, "Glorious Anthem"); // enchantment {1}{W}{W}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Heliod, God of the Sun"); // as creature
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Heliod isn't a creature on the battlefield (devotion 1), but it was cast as enchantment creature
        checkPlayableAbility("enchantment is free", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Glorious Anthem", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Glorious Anthem"); // as enchantment
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertNotType("Heliod, God of the Sun", CardType.CREATURE); // devotion 1 + 2
        assertPermanentCount(playerA, "Glorious Anthem", 1);
    }

    @Test
    public void test_PlayedLandGainsType_MustNotFreeLandPlay() {
        // During each of your turns, you may play a land and cast a permanent spell of each permanent type from
        // your graveyard. (If a card has multiple permanent types, choose one as you play it.)
        addCard(Zone.BATTLEFIELD, playerA, "Muldrotha, the Gravetide");
        // You may play an additional land on each of your turns.
        addCard(Zone.BATTLEFIELD, playerA, "Exploration");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        //
        // {1}: Mutavault becomes a 2/2 creature with all creature types until end of turn. It's still a land.
        addCard(Zone.GRAVEYARD, playerA, "Mutavault");
        addCard(Zone.GRAVEYARD, playerA, "Swamp");
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears"); // {1}{G} creature 2/2

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mutavault");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}:");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Mutavault is a land creature now, but it was played as land only
        checkPlayableAbility("land is still used", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Swamp", false);
        checkPlayableAbility("creature is free", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Grizzly Bears", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertType("Mutavault", CardType.CREATURE, true);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerA, "Swamp", 1);
    }

    @Test
    public void test_OpponentTurn_CantCast() {
        // During each of your turns, you may play a land and cast a permanent spell of each permanent type from
        // your graveyard. (If a card has multiple permanent types, choose one as you play it.)
        addCard(Zone.BATTLEFIELD, playerA, "Muldrotha, the Gravetide");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        //
        // Flash
        // Deathtouch
        addCard(Zone.GRAVEYARD, playerA, "Ambush Viper"); // {1}{G} creature 2/1

        checkPlayableAbility("opponent's turn", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Ambush Viper", false);
        checkPlayableAbility("opponent's turn", 2, PhaseStep.END_TURN, playerA, "Cast Ambush Viper", false);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Ambush Viper");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Ambush Viper", 1);
    }
}
