package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class WorldgorgerDragonTest extends CardTestPlayerBase {

    /**
     * Tests that exiled permanents return to battlefield
     */
    @Test
    public void testDisabledEffectOnChangeZone() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        // Flying
        // Trample
        // When Worldgorger Dragon enters the battlefield, exile all other permanents you control.
        // When Worldgorger Dragon leaves the battlefield, return the exiled cards to the battlefield under their owners' control.
        addCard(Zone.HAND, playerA, "Worldgorger Dragon");

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Gerrard's Battle Cry", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        // Destroy target nonartifact, nonblack creature. It can't be regenerated.
        addCard(Zone.HAND, playerB, "Terror", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Worldgorger Dragon");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Terror", "Worldgorger Dragon");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Worldgorger Dragon", 1);
        assertGraveyardCount(playerB, "Terror", 1);

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPermanentCount(playerA, "Gerrard's Battle Cry", 1);

    }

    /*
     * 1. Cast Animate Dead, targeting the Dragon
     * 2. Dragon comes into play, it's ability goes on the stack.
     * 3. The ability resolves, and all my other permanents leave play
     * 4. Since Animate Dead left play, Dragon goes to the graveyard
     * 5. Since the Dragon left play, the land and Animate Dead return to play. Animate Dead triggers, targeting the Dragon.
     * 6. In response to Animate Dead's ability going on the stack, tap the lands for mana.
     * 7. Animate Dead resolves, Dragon comes into play, everything else leaves play.
     * 8. Steps 4-7 repeat endlessly. Your mana pool fills.
     * 9. You can interrupt the sequence to play an instant.
     */
    @Test
    public void testWithAnimateDead() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        // When Worldgorger Dragon enters the battlefield, exile all other permanents you control.
        // When Worldgorger Dragon leaves the battlefield, return the exiled cards to the battlefield under their owners' control.
        addCard(Zone.GRAVEYARD, playerA, "Worldgorger Dragon", 1);
        // Enchant creature card in a graveyard
        // When Animate Dead enters the battlefield, if it's on the battlefield, it loses "enchant creature card in a graveyard"
        // and gains "enchant creature put onto the battlefield with Animate Dead." Return enchanted creature card to the battlefield
        // under your control and attach Animate Dead to it. When Animate Dead leaves the battlefield, that creature's controller sacrifices it.
        // Enchanted creature gets -1/-0.
        addCard(Zone.HAND, playerA, "Animate Dead");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        // Instant {X}{R}{R}
        // Volcanic Geyser deals X damage to any target.
        addCard(Zone.HAND, playerA, "Volcanic Geyser", 1);
        // When Staunch Defenders enters the battlefield, you gain 4 life.
        addCard(Zone.BATTLEFIELD, playerA, "Staunch Defenders", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Animate Dead", "Worldgorger Dragon");
        setChoice(playerA, "Worldgorger Dragon");
        setChoice(playerA, "When {this} enters the battlefield, if it's");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");

        setChoice(playerA, "Worldgorger Dragon");
        setChoice(playerA, "When {this} enters the battlefield, if it's");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");

        setChoice(playerA, "Worldgorger Dragon");
        setChoice(playerA, "When {this} enters the battlefield, if it's");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");

        setChoice(playerA, "Worldgorger Dragon");
        setChoice(playerA, "When {this} enters the battlefield, if it's");
        setChoice(playerA, false);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");

        setChoice(playerA, "Worldgorger Dragon");
        setChoice(playerA, "When {this} enters the battlefield, if it's");

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");

        setChoice(playerA, "Worldgorger Dragon");
        setChoice(playerA, "When {this} enters the battlefield, if it's");

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");

        setChoice(playerA, "Worldgorger Dragon");
        setChoice(playerA, "When {this} enters the battlefield, if it's");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Volcanic Geyser", playerB, 22);
        setChoice(playerA, "X=20");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 44);
        assertLife(playerB, 0);

        assertGraveyardCount(playerA, "Volcanic Geyser", 1);

    }

    /**
     * v9: Worldgorger Dragon + Animate Dead is still acting up (yet complex
     * rules interactions!). The first time you return Animate Dead from
     * Worldgorger's exile, it works like it's supposed to. You have to pick a
     * creature, and it brings it back. But if you pick Worldgorger Dragon
     * again, it allows you to not pick a creature, and regardless of whether
     * you choose to skip or pick a different creature, it always returns the
     * first creature you picked. Kind of hard to explain, but here's how to
     * reproduce:
     *
     * 1) Cast Animate Dead, targeting Worldgorger Dragon 2) Worldgorger Dragon
     * will exile Animate Dead, killing the dragon and returning the permanents
     * 3) Select Worldgorger again 4) Step 2 repeats 5) Attempt to select a
     * different creature. Worldgorger Dragon is returned instead.
     *
     */
    @Test
    public void testWithAnimateDeadDifferentTargets() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        // When Worldgorger Dragon enters the battlefield, exile all other permanents you control.
        // When Worldgorger Dragon leaves the battlefield, return the exiled cards to the battlefield under their owners' control.
        addCard(Zone.GRAVEYARD, playerA, "Worldgorger Dragon", 1);

        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion", 1);
        // When Animate Dead enters the battlefield, if it's on the battlefield, it loses "enchant creature card in a graveyard"
        // and gains "enchant creature put onto the battlefield with Animate Dead." Return enchanted creature card to the battlefield
        // under your control and attach Animate Dead to it. When Animate Dead leaves the battlefield, that creature's controller sacrifices it.
        addCard(Zone.HAND, playerA, "Animate Dead");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        // Volcanic Geyser deals X damage to any target.
        addCard(Zone.HAND, playerA, "Volcanic Geyser", 1);// Instant {X}{R}{R}
        // When Staunch Defenders enters the battlefield, you gain 4 life.
        addCard(Zone.BATTLEFIELD, playerA, "Staunch Defenders", 1);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Animate Dead", "Worldgorger Dragon");

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        setChoice(playerA, "Worldgorger Dragon");
        setChoice(playerA, "When {this} enters the battlefield, if it's");

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        setChoice(playerA, "Silvercoat Lion");

        setChoice(playerA, "When {this} enters the battlefield, if it's");

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Volcanic Geyser", playerB, 9);
        setChoice(playerA, "X=9");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Volcanic Geyser", 1);
        assertGraveyardCount(playerA, "Worldgorger Dragon", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);

        assertLife(playerA, 28);
        assertLife(playerB, 11);

        Assert.assertEquals("Mana pool", "[]", playerA.getManaAvailable(currentGame).toString());

    }

}
