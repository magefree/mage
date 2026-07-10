
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.command.emblems.WrennAndSixEmblem;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class RetraceTest extends CardTestPlayerBase {

    /**
     * 702.78. Retrace 702.78a Retrace appears on some instants and sorceries.
     * It represents a static ability that functions while the card is in a
     * player's graveyard. "Retrace" means "You may cast this card from your
     * graveyard by discarding a land card as an additional cost to cast it."
     * Casting a spell using its retrace ability follows the rules for paying
     * additional costs in rules 601.2b and 601.2e-g.
     */
    @Test
    public void SimpleRetrace() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        // Target player discards a card.
        // Retrace
        addCard(Zone.GRAVEYARD, playerA, "Raven's Crime");
        addCard(Zone.HAND, playerA, "Swamp");

        addCard(Zone.HAND, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raven's Crime with retrace", playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Raven's Crime", 1);
        assertGraveyardCount(playerA, "Swamp", 1);

        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
    }

    /**
     * Test that it does cost {1}{B} + land discard
     */
    @Test
    public void RetraceCostIncreaseCantPay() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        // Target player discards a card.
        // Retrace
        addCard(Zone.GRAVEYARD, playerA, "Raven's Crime");
        addCard(Zone.HAND, playerA, "Swamp");

        // // Noncreature spells cost {1} more to cast.
        addCard(Zone.BATTLEFIELD, playerB, "Thalia, Guardian of Thraben", 1);
        addCard(Zone.HAND, playerB, "Silvercoat Lion", 1);

        checkPlayableAbility("Check price increase", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Raven's", false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Raven's Crime", 1);
        assertGraveyardCount(playerA, "Swamp", 0); // because not enough mana

        assertGraveyardCount(playerB, "Silvercoat Lion", 0); // because not enough mana
    }

    /**
     * Test that it does cost {B}{1} + land discard
     */
    @Test
    public void RetraceCostIncreaseCanPay() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        // Target player discards a card.
        // Retrace
        addCard(Zone.GRAVEYARD, playerA, "Raven's Crime");
        addCard(Zone.HAND, playerA, "Swamp");

        // // Noncreature spells cost {1} more to cast.
        addCard(Zone.BATTLEFIELD, playerB, "Thalia, Guardian of Thraben", 1);
        addCard(Zone.HAND, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raven's Crime with retrace", playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Raven's Crime", 1);
        assertGraveyardCount(playerA, "Swamp", 1);

        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
    }

    /**
     * I noticed the other day that I was not able to cast Worm Harvest from the
     * graveyard. I'm not sure if this is an issue with all cards with the
     * "retrace" ability but I figured it should be mentioned!
     */
    @Test
    public void RetraceCastFromGraveyard() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        // Target player discards a card.
        // Retrace
        addCard(Zone.GRAVEYARD, playerA, "Worm Harvest");
        addCard(Zone.GRAVEYARD, playerA, "Swamp", 2);
        addCard(Zone.HAND, playerA, "Mountain", 1);

        // Create a 1/1 black and green Worm creature token for each land card in your graveyard.
        // Retrace (You may cast this card from your graveyard by discarding a land card in addition to paying its other costs.)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Worm Harvest with retrace");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Worm Token", 3);

        assertGraveyardCount(playerA, "Mountain", 1);

    }
    
    @Test
    public void test_Retrace_WithEvokeAlternativeCost() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Six");
        addCard(Zone.GRAVEYARD, playerA, "Solitude");
        addCard(Zone.HAND, playerA, "Plains");
        addCard(Zone.HAND, playerA, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Solitude with retrace");
        setChoice(playerA, "Cast with Evoke alternative cost");
        setChoice(playerA, "Silvercoat Lion"); // exile for evoke
        setChoice(playerA, "Plains"); // discard for retrace
        setChoice(playerA, "When this permanent enters, if its evoke cost was paid, its controller sacrifices it");
        addTarget(playerA, TestPlayer.TARGET_SKIP); // no target for Solitude's enters ability

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, "Silvercoat Lion", 1);
        assertGraveyardCount(playerA, "Plains", 1);
        assertGraveyardCount(playerA, "Solitude", 1); // sacrificed after being evoked
    }

    @Test
    public void test_Retrace_DoesNotUseOtherGraveyardPermission() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Six");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.GRAVEYARD, playerA, "Demilich");
        addCard(Zone.GRAVEYARD, playerA, "Opt", 4);
        addCard(Zone.HAND, playerA, "Plains");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Demilich with retrace");
        setChoice(playerA, "Plains"); // discard for retrace

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Demilich", 1);
        assertGraveyardCount(playerA, "Plains", 1);
        assertGraveyardCount(playerA, "Opt", 4);
    }

    @Test
    public void test_Retrace_DemonicEmbraceDoesNotUseOwnGraveyardPermission() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Six");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.GRAVEYARD, playerA, "Demonic Embrace");
        addCard(Zone.HAND, playerA, "Plains");
        addCard(Zone.HAND, playerA, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Demonic Embrace with retrace", "Grizzly Bears");
        setChoice(playerA, "Plains"); // discard for retrace

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Demonic Embrace", 1);
        assertGraveyardCount(playerA, "Plains", 1);
        assertHandCount(playerA, "Silvercoat Lion", 1);
        assertLife(playerA, 20);
    }

    @Test
    public void test_Retrace_RonaDoesNotUseOwnGraveyardPermission() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Six");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.GRAVEYARD, playerA, "Rona, Sheoldred's Faithful");
        addCard(Zone.HAND, playerA, "Plains");
        addCard(Zone.HAND, playerA, "Silvercoat Lion");
        addCard(Zone.HAND, playerA, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rona, Sheoldred's Faithful with retrace");
        setChoice(playerA, "Plains"); // discard for retrace

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Rona, Sheoldred's Faithful", 1);
        assertGraveyardCount(playerA, "Plains", 1);
        assertHandCount(playerA, "Silvercoat Lion", 1);
        assertHandCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void test_Retrace_WithFiresOfInvention() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Fires of Invention");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addEmblem(playerA, new WrennAndSixEmblem());
        addCard(Zone.GRAVEYARD, playerA, "Shock");
        addCard(Zone.HAND, playerA, "Forest");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock with retrace", playerB);
        setChoice(playerA, "Cast without paying its mana cost");
        setChoice(playerA, "Forest"); // discard for retrace

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 18);
        assertGraveyardCount(playerA, "Shock", 1);
        assertGraveyardCount(playerA, "Forest", 1);
    }
}
