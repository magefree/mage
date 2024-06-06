package org.mage.test.cards.single.jmp;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class ScholarOfTheLostTroveTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.ScholarOfTheLostTrove Scholar of the Lost Trove} {5}{U}{U}
     * Creature — Sphinx
     * Flying
     * When Scholar of the Lost Trove enters the battlefield, you may cast target instant, sorcery, or artifact card from your graveyard without paying its mana cost. If an instant or sorcery spell cast this way would be put into your graveyard, exile it instead.
     * 5/5
     */
    private static final String scholar = "Scholar of the Lost Trove";

    @Test
    public void test_Target_Artifact() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        addCard(Zone.HAND, playerA, scholar);
        addCard(Zone.GRAVEYARD, playerA, "Orzhov Signet");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, scholar);
        addTarget(playerA, "Orzhov Signet");
        setChoice(playerA, true); // yes to "you may cast"

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Orzhov Signet", 1);
    }

    @Test
    public void test_Target_Artifact_ChooseNotToCast() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        addCard(Zone.HAND, playerA, scholar);
        addCard(Zone.GRAVEYARD, playerA, "Orzhov Signet");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, scholar);
        addTarget(playerA, "Orzhov Signet");
        setChoice(playerA, false); // No to "you may cast"

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Orzhov Signet", 1);
    }

    @Test
    public void test_Target_Sorcery() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        addCard(Zone.HAND, playerA, scholar);
        addCard(Zone.GRAVEYARD, playerA, "Divination");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, scholar);
        addTarget(playerA, "Divination");
        setChoice(playerA, true); // yes to "you may cast"

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, "Divination", 1);
        assertHandCount(playerA, 2);
    }

    @Test
    public void test_Target_Split() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        addCard(Zone.HAND, playerA, scholar);
        addCard(Zone.GRAVEYARD, playerA, "Fire // Ice");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, scholar);
        addTarget(playerA, "Fire // Ice");
        setChoice(playerA, true); // yes to "you may cast"
        setChoice(playerA, "Cast Ice");
        addTarget(playerA, scholar);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, "Fire // Ice", 1);
        assertHandCount(playerA, 1);
        assertTappedCount(scholar, true, 1);
    }
}
