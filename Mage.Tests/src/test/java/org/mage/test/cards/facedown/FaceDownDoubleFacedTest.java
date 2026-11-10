package org.mage.test.cards.facedown;

import mage.constants.EmptyNames;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DarioBarrabinoDev
 */
public class FaceDownDoubleFacedTest extends CardTestPlayerBase {

    // 712.16. Melded permanents and other double-faced permanents can't be turned face down. If a spell or ability
    // tries to turn a double-faced permanent face down, nothing happens and that effect doesn't change any of its
    // characteristics or their copiable values.
    // 730.2j A face-up merged permanent that contains a double-faced component can't be turned face down. [...]

    // As Ixidron enters, turn all other nontoken creatures face down. (They're 2/2 creatures.)
    // Ixidron's power and toughness are each equal to the number of face-down creatures on the battlefield.
    private static final String ixidron = "Ixidron";
    private static final String bear = "Grizzly Bears";

    private void castIxidron(int turn) {
        addCard(Zone.HAND, playerA, ixidron);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        // control: a regular creature must be turned face down (and keeps Ixidron alive as a 1/1)
        addCard(Zone.BATTLEFIELD, playerB, bear);
        castSpell(turn, PhaseStep.PRECOMBAT_MAIN, playerA, ixidron);
    }

    private void assertOnlyBearFaceDown(String faceUpName) {
        assertPermanentCount(playerB, bear, 0);
        assertPermanentCount(playerB, EmptyNames.FACE_DOWN_CREATURE.getTestCommand(), 1);
        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.getTestCommand(), 0);
        assertPermanentCount(playerA, faceUpName, 1);
        assertPowerToughness(playerA, ixidron, 1, 1);
    }

    @Test
    public void test_TransformingDoubleFaced_StaysFaceUp() {
        // Werewolf Horror 2/1, {4}{G}: Transform
        addCard(Zone.BATTLEFIELD, playerA, "Kessig Prowler");
        castIxidron(1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertOnlyBearFaceDown("Kessig Prowler");
        assertPowerToughness(playerA, "Kessig Prowler", 2, 1);
    }

    @Test
    public void test_ModalDoubleFaced_StaysFaceUp() {
        // Elephant 3/3 // land
        addCard(Zone.BATTLEFIELD, playerA, "Kazandu Mammoth");
        castIxidron(1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertOnlyBearFaceDown("Kazandu Mammoth");
        assertPowerToughness(playerA, "Kazandu Mammoth", 3, 3);
    }

    @Test
    public void test_Melded_StaysFaceUp() {
        // at the beginning of your end step, meld into Brisela, Voice of Nightmares (9/10)
        addCard(Zone.BATTLEFIELD, playerA, "Bruna, the Fading Light");
        addCard(Zone.BATTLEFIELD, playerA, "Gisela, the Broken Blade");
        setChoice(playerA, "Bruna, the Fading Light"); // meld on turn 1 end step
        castIxidron(3);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertOnlyBearFaceDown("Brisela, Voice of Nightmares");
        assertPowerToughness(playerA, "Brisela, Voice of Nightmares", 9, 10);
    }

    @Test
    public void test_MutatedOverDoubleFaced_StaysFaceUp() {
        // Mutate {3}{U}, 3/4 flying
        addCard(Zone.HAND, playerA, "Dreamtail Heron");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Kazandu Mammoth");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dreamtail Heron using Mutate", "Kazandu Mammoth");
        setChoice(playerA, false); // put it over

        castIxidron(3);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertOnlyBearFaceDown("Dreamtail Heron");
        assertPowerToughness(playerA, "Dreamtail Heron", 3, 4);
    }
}
