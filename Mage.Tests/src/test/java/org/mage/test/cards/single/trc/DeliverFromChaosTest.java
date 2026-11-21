package org.mage.test.cards.single.trc;

import mage.constants.CardType;
import mage.constants.EmptyNames;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DarioBarrabinoDev, JayDi85
 */
public class DeliverFromChaosTest extends CardTestPlayerBase {

    @Test
    public void test_CreatureTarget_ExileAndCreateBorg() {
        // Exile target artifact or creature. Create X 1/1 colorless Borg artifact creature tokens,
        // where X is that permanent's mana value.
        addCard(Zone.HAND, playerA, "Deliver from Chaos"); // {3}{W}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Gray Ogre"); // mana value 3

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Deliver from Chaos", "Gray Ogre");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Gray Ogre", 0);
        assertExileCount(playerB, "Gray Ogre", 1);
        assertPermanentCount(playerA, "Borg Token", 3);
        assertPowerToughness(playerA, "Borg Token", 1, 1);
        assertType("Borg Token", CardType.ARTIFACT, SubType.BORG);
        assertType("Borg Token", CardType.CREATURE, true);
        assertColor(playerA, "Borg Token", "WUBRG", false);
    }

    @Test
    public void test_ZeroManaValueArtifact_ExileWithoutBorg() {
        // Exile target artifact or creature. Create X 1/1 colorless Borg artifact creature tokens,
        // where X is that permanent's mana value.
        addCard(Zone.HAND, playerA, "Deliver from Chaos"); // {3}{W}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Lotus Petal"); // mana value 0

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Deliver from Chaos", "Lotus Petal");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Lotus Petal", 0);
        assertExileCount(playerB, "Lotus Petal", 1);
        assertPermanentCount(playerA, "Borg Token", 0);
    }

    @Test
    public void test_NoncopyToken_ExileWithoutBorg() {
        // Exile target artifact or creature. Create X 1/1 colorless Borg artifact creature tokens,
        // where X is that permanent's mana value.
        addCard(Zone.HAND, playerA, "Deliver from Chaos"); // {3}{W}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        //
        // Create two 1/1 red Goblin creature tokens.
        addCard(Zone.HAND, playerA, "Dragon Fodder"); // {1}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}", 2); // keep the white and black sources for Deliver from Chaos
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dragon Fodder");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Deliver from Chaos", "Goblin Token"); // exile own token

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Goblin Token", 1);
        assertPermanentCount(playerA, "Borg Token", 0);
    }

    @Test
    public void test_OwnArtifact_ExileAndCreateBorg() {
        // Exile target artifact or creature. Create X 1/1 colorless Borg artifact creature tokens,
        // where X is that permanent's mana value.
        addCard(Zone.HAND, playerA, "Deliver from Chaos"); // {3}{W}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Prophetic Prism"); // noncreature artifact, mana value 2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Deliver from Chaos", "Prophetic Prism"); // own artifact is legal

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, "Prophetic Prism", 1);
        assertPermanentCount(playerA, "Borg Token", 2);
        assertPermanentCount(playerB, "Borg Token", 0);
    }

    @Test
    public void test_ExiledCopy_UseLastKnownManaValue() {
        // Deliver from Chaos
        // Use the mana value of the exiled permanent as it last existed on the battlefield to
        // determine the value of X.
        // (2026-10-07)

        // Exile target artifact or creature. Create X 1/1 colorless Borg artifact creature tokens,
        // where X is that permanent's mana value.
        addCard(Zone.HAND, playerA, "Deliver from Chaos"); // {3}{W}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        //
        // You may have this creature enter as a copy of any creature on the battlefield.
        addCard(Zone.HAND, playerB, "Clone@clone"); // {3}{U}, printed mana value 4
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Gray Ogre"); // mana value 3

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Clone");
        setChoice(playerB, true); // enter as a copy
        setChoice(playerB, "Gray Ogre"); // the copy has mana value 3

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Deliver from Chaos", "@clone"); // exile the copy

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerB, "Clone", 1);
        assertPermanentCount(playerB, "Gray Ogre", 0);
        assertPermanentCount(playerA, "Gray Ogre", 1);
        assertPermanentCount(playerA, "Borg Token", 3);
    }

    @Test
    public void test_TokenCopy_ExileAndCreateBorg() {
        // Exile target artifact or creature. Create X 1/1 colorless Borg artifact creature tokens,
        // where X is that permanent's mana value.
        addCard(Zone.HAND, playerA, "Deliver from Chaos"); // {3}{W}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        //
        // Create a token that's a copy of target creature you control.
        addCard(Zone.HAND, playerB, "Cackling Counterpart"); // {1}{U}{U}
        addCard(Zone.BATTLEFIELD, playerB, "Island", 3);
        //
        // Return target creature to its owner's hand.
        addCard(Zone.HAND, playerB, "Unsummon"); // {U}
        addCard(Zone.BATTLEFIELD, playerB, "Island");
        addCard(Zone.BATTLEFIELD, playerB, "Gray Ogre@original"); // mana value 3

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}: Add {U}", 3); // leave one Island for Unsummon
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Cackling Counterpart", "Gray Ogre"); // copy the creature and its mana cost

        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, "Unsummon", "@original"); // leave just the token on the battlefield

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Deliver from Chaos", "Gray Ogre"); // exile the token copy

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Gray Ogre", 0);
        assertHandCount(playerB, "Gray Ogre", 1); // the nontoken original is in hand
        assertPermanentCount(playerA, "Borg Token", 3);
    }

    @Test
    public void test_FaceDownCreature_ExileWithoutBorg() {
        // Exile target artifact or creature. Create X 1/1 colorless Borg artifact creature tokens,
        // where X is that permanent's mana value.
        addCard(Zone.HAND, playerA, "Deliver from Chaos"); // {3}{W}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        //
        // Morph — You may cast this card face down as a 2/2 creature for {3}.
        addCard(Zone.HAND, playerB, "Exalted Angel"); // {3} face down, printed mana value 6
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 3);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Exalted Angel using Morph");

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Deliver from Chaos", EmptyNames.FACE_DOWN_CREATURE.getTestCommand()); // exile the face-down creature

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerB, "Exalted Angel", 1);
        assertPermanentCount(playerB, EmptyNames.FACE_DOWN_CREATURE.getTestCommand(), 0);
        assertPermanentCount(playerA, "Borg Token", 0);
    }

    @Test
    public void test_TargetBlinks_SpellDoesNotResolve() {
        // Deliver from Chaos
        // If the target permanent is an illegal target as Deliver from Chaos tries to resolve, it
        // won't resolve and none of its effects will happen. You won't create any Borg tokens.
        // (2026-10-07)

        // Exile target artifact or creature. Create X 1/1 colorless Borg artifact creature tokens,
        // where X is that permanent's mana value.
        addCard(Zone.HAND, playerA, "Deliver from Chaos"); // {3}{W}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Gray Ogre"); // mana value 3
        // Exile target creature you control, then return that card to the battlefield under your control.
        addCard(Zone.HAND, playerB, "Cloudshift"); // {W}
        addCard(Zone.BATTLEFIELD, playerB, "Plains");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Deliver from Chaos", "Gray Ogre"); // target the original Ogre

        // The returned creature is a new object and cannot be the original target.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Cloudshift", "Gray Ogre", "Deliver from Chaos");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Deliver from Chaos", 1);
        assertPermanentCount(playerB, "Gray Ogre", 1);
        assertExileCount(playerB, "Gray Ogre", 0);
        assertPermanentCount(playerA, "Borg Token", 0);
    }
}
