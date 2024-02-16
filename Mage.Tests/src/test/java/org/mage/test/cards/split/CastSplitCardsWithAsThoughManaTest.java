package org.mage.test.cards.split;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class CastSplitCardsWithAsThoughManaTest extends CardTestPlayerBase {

    @Test
    public void test_AsThoughMana_Simple() {
        // {1}{R}
        // When Dire Fleet Daredevil enters the battlefield, exile target instant or sorcery card from an opponent’s graveyard.
        // You may cast that card this turn, and you may spend mana as though it were mana of any type to cast that spell.
        // If that card would be put into a graveyard this turn, exile it instead.
        addCard(Zone.HAND, playerA, "Dire Fleet Daredevil", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        //
        addCard(Zone.GRAVEYARD, playerB, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);

        // cast fleet
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dire Fleet Daredevil");
        addTarget(playerA, "Lightning Bolt");

        // cast bolt with blue mana
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20 - 3);
    }

    @Test
    public void test_AsThoughMana_Split_WearTear() {
        // {1}{R}
        // When Dire Fleet Daredevil enters the battlefield, exile target instant or sorcery card from an opponent’s graveyard.
        // You may cast that card this turn, and you may spend mana as though it were mana of any type to cast that spell.
        // If that card would be put into a graveyard this turn, exile it instead.
        addCard(Zone.HAND, playerA, "Dire Fleet Daredevil", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        //
        // Wear {1}{R} Destroy target artifact.
        // Tear {W} Destroy target enchantment.
        addCard(Zone.GRAVEYARD, playerB, "Wear // Tear", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Bident of Thassa", 1); // Legendary Enchantment Artifact
        addCard(Zone.BATTLEFIELD, playerB, "Bow of Nylea", 1); // Legendary Enchantment Artifact

        // cast fleet
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dire Fleet Daredevil");
        addTarget(playerA, "Wear // Tear");

        // cast Wear with black mana
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wear", "Bident of Thassa");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Bident of Thassa", 1);
        assertPermanentCount(playerB, "Bow of Nylea", 1);
    }

    @Test
    public void test_AsThoughMana_Split_CatchRelease() {
        // {1}{R}
        // When Dire Fleet Daredevil enters the battlefield, exile target instant or sorcery card from an opponent’s graveyard.
        // You may cast that card this turn, and you may spend mana as though it were mana of any type to cast that spell.
        // If that card would be put into a graveyard this turn, exile it instead.
        addCard(Zone.HAND, playerA, "Dire Fleet Daredevil", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        //
        // Catch {1}{U}{R} Gain control of target permanent until end of turn. Untap it. It gains haste until end of turn.
        // Release {4}{R}{W} Each player sacrifices an artifact, a creature, an enchantment, a land, and a planeswalker.
        addCard(Zone.GRAVEYARD, playerB, "Catch // Release", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1);

        // cast fleet
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dire Fleet Daredevil");
        addTarget(playerA, "Catch // Release");

        // cast Catch with black mana
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Catch", "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Balduvian Bears", 1);
        assertPermanentCount(playerB, "Balduvian Bears", 0);
    }
}
