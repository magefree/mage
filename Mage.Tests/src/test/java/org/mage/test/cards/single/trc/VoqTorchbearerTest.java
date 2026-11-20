package org.mage.test.cards.single.trc;

import mage.abilities.keyword.MenaceAbility;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class VoqTorchbearerTest extends CardTestPlayerBase {

    @Test
    public void test_CopyCreatureCardInGraveyard() {
        // ChoH'a' — You may have Voq enter as a copy of a creature card in a graveyard, except he's a Klingon Spy
        // in addition to his other types and has menace.
        addCard(Zone.HAND, playerA, "Voq, Torchbearer"); // {3}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        //
        addCard(Zone.GRAVEYARD, playerB, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Voq, Torchbearer");
        setChoice(playerA, true); // use ChoH'a'
        setChoice(playerA, "Grizzly Bears"); // copy bear from opponent's graveyard

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Voq, Torchbearer", 0);
        assertPowerToughness(playerA, "Grizzly Bears", 2, 2);
        // keep
        assertSubtype("Grizzly Bears", SubType.BEAR);
        // gain
        assertSubtype("Grizzly Bears", SubType.KLINGON);
        assertSubtype("Grizzly Bears", SubType.SPY);
        assertAbility(playerA, "Grizzly Bears", new MenaceAbility(), true);
    }

    @Test
    public void test_DontCopy() {
        // ChoH'a' — You may have Voq enter as a copy of a creature card in a graveyard, except he's a Klingon Spy
        // in addition to his other types and has menace.
        addCard(Zone.HAND, playerA, "Voq, Torchbearer"); // {3}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        //
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Voq, Torchbearer");
        setChoice(playerA, false); // don't use ChoH'a'

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Voq, Torchbearer", 4, 3);
        assertPermanentCount(playerA, "Grizzly Bears", 0);
    }

    @Test
    public void test_CopiedEntersAbilityWorks() {
        // Body Double ruling (same "enter as a copy of a creature card in a graveyard" ability):
        // Treat Body Double as though it were the chosen card entering. Any "As this card enters,"
        // "This card enters with," and "When this card enters" abilities of the chosen card will work.
        // (2007-02-01)
        addCard(Zone.HAND, playerA, "Voq, Torchbearer"); // {3}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        //
        addCard(Zone.GRAVEYARD, playerA, "Elvish Visionary"); // when enters, draw a card

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Voq, Torchbearer");
        setChoice(playerA, true); // use ChoH'a'
        setChoice(playerA, "Elvish Visionary");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Elvish Visionary", 1);
        assertHandCount(playerA, 1); // draw by the copied enters ability
    }

    @Test
    public void test_CopyDoubleFacedCard() {
        // ChoH'a' — You may have Voq enter as a copy of a creature card in a graveyard, except he's a Klingon Spy
        // in addition to his other types and has menace.
        addCard(Zone.HAND, playerA, "Voq, Torchbearer"); // {3}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        //
        addCard(Zone.GRAVEYARD, playerA, "Delver of Secrets"); // transforming double-faced card, 1/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Voq, Torchbearer");
        setChoice(playerA, true); // use ChoH'a'
        setChoice(playerA, "Delver of Secrets");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Delver of Secrets", 1, 1);
        assertSubtype("Delver of Secrets", SubType.WIZARD);
        assertSubtype("Delver of Secrets", SubType.KLINGON);
    }

    @Test
    public void test_Blink_NewObjectChoosesAgain() {
        // ChoH'a' — You may have Voq enter as a copy of a creature card in a graveyard, except he's a Klingon Spy
        // in addition to his other types and has menace.
        addCard(Zone.HAND, playerA, "Voq, Torchbearer"); // {3}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.HAND, playerA, "Cloudshift"); // {W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        //
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears"); // bear 2/2

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}", 4);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Voq, Torchbearer");
        setChoice(playerA, true); // use ChoH'a'
        setChoice(playerA, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cloudshift", "Grizzly Bears");
        setChoice(playerA, false); // returned Voq doesn't use ChoH'a'

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Voq, Torchbearer", 4, 3);
        assertPermanentCount(playerA, "Grizzly Bears", 0);
    }
}
