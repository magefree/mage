package org.mage.test.cards.single.mh3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * @author Susucr
 */
public class NethergoyfTest extends CardTestPlayerBaseWithAIHelps {

    /**
     * {@link mage.cards.n.Nethergoyf Nethergoyf} {B}
     * Creature — Lhurgoyf
     * Nethergoyf’s power is equal to the number of card types among cards in your graveyard and its toughness is equal to that number plus 1.
     * Escape—{2}{B}, Exile any number of other cards from your graveyard with four or more card types among them. (You may cast this card from your graveyard for its escape cost.)
     * * / 1+*
     */
    private static final String nethergoyf = "Nethergoyf";

    @Test
    public void test_Escape_Two_DualTypes() {
        setStrictChooseMode(true);

        addCard(Zone.GRAVEYARD, playerA, nethergoyf);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.GRAVEYARD, playerA, "Memnite"); // Creature Artifact
        addCard(Zone.GRAVEYARD, playerA, "Bitterblossom"); // Tribal Enchantment

        checkPlayableAbility("can escape", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + nethergoyf + " with Escape", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, nethergoyf + " with Escape");
        setChoice(playerA, "Memnite^Bitterblossom"); // cards exiled for escape cost

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, nethergoyf, 1);
        assertPowerToughness(playerA, nethergoyf, 0, 1);
        assertExileCount(playerA, 2);
    }

    @Test
    public void test_AI_Escape_Two_DualTypes() {
        setStrictChooseMode(true);

        addCard(Zone.GRAVEYARD, playerA, nethergoyf);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.GRAVEYARD, playerA, "Memnite"); // Creature Artifact
        addCard(Zone.GRAVEYARD, playerA, "Bitterblossom"); // Tribal Enchantment

        checkPlayableAbility("can escape", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + nethergoyf + " with Escape", true);
        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, nethergoyf, 1);
        assertPowerToughness(playerA, nethergoyf, 0, 1);
        assertExileCount(playerA, 2);
    }

    @Test
    public void test_Escape_MoreCardsThanNeeded() {
        setStrictChooseMode(true);

        addCard(Zone.GRAVEYARD, playerA, nethergoyf);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.GRAVEYARD, playerA, "Memnite", 5); // Creature Artifact
        addCard(Zone.GRAVEYARD, playerA, "Bitterblossom"); // Tribal Enchantment

        checkPlayableAbility("can escape", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + nethergoyf + " with Escape", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, nethergoyf + " with Escape");
        setChoice(playerA, "Memnite^Memnite^Memnite^Memnite^Bitterblossom"); // cards exiled for escape cost: Exile all the Memnite but one.

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, nethergoyf, 1);
        assertPowerToughness(playerA, nethergoyf, 2, 3); // 1 Memnite in graveyard
        assertExileCount(playerA, 5);
        assertGraveyardCount(playerA, 1);
    }

    @Test
    public void test_AI_Escape_MoreCardsThanNeeded() {
        setStrictChooseMode(true);

        addCard(Zone.GRAVEYARD, playerA, nethergoyf);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.GRAVEYARD, playerA, "Bitterblossom"); // Tribal Enchantment
        addCard(Zone.GRAVEYARD, playerA, "Memnite", 5); // Creature Artifact

        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, nethergoyf, 1);
        assertPowerToughness(playerA, nethergoyf, 0, 1);
        assertExileCount(playerA, 6); // It is weird, but AI likes to choose all to be exiled, even though the Outcome is Exile (so detriment)
        assertGraveyardCount(playerA, 0);
    }


    @Test
    public void test_CantEscape_Without4TypesInGraveyard() {
        setStrictChooseMode(true);

        addCard(Zone.GRAVEYARD, playerA, nethergoyf);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.GRAVEYARD, playerA, "Taiga"); // Land
        addCard(Zone.GRAVEYARD, playerA, "Bitterblossom"); // Tribal Enchantment

        checkPlayableAbility("can't escape", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + nethergoyf + " with Escape", false);
        // 3 types from other cards in graveyard, Nethergoyf can't escape

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, 3);
    }

    @Test
    public void test_AI_CantEscape_Without4TypesInGraveyard() {
        setStrictChooseMode(true);

        addCard(Zone.GRAVEYARD, playerA, nethergoyf);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.GRAVEYARD, playerA, "Taiga"); // Land
        addCard(Zone.GRAVEYARD, playerA, "Bitterblossom"); // Tribal Enchantment

        checkPlayableAbility("can't escape", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + nethergoyf + " with Escape", false);
        // 3 types from other cards in graveyard, Nethergoyf can't escape
        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, 3);
    }

    @Test
    public void test_DynamicGameType() {
        setStrictChooseMode(true);

        addCard(Zone.GRAVEYARD, playerA, nethergoyf);
        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 7);
        addCard(Zone.GRAVEYARD, playerA, "Taiga"); // Land
        addCard(Zone.GRAVEYARD, playerA, "Grist, the Hunger Tide"); // Planeswalker, is a Creature if not in play
        // Nonland permanents you control are artifacts in addition to their other types.
        // The same is true for permanent spells you control and nonland permanent cards you own that aren’t on the battlefield.
        addCard(Zone.HAND, playerA, "Encroaching Mycosynth");

        checkPlayableAbility("1: can't escape", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + nethergoyf + " with Escape", false);
        // 3 types from other cards in graveyard, Nethergoyf can't escape
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Encroaching Mycosynth", true);
        // After Mycosynth in play, Grist is now an Artifact in addition to its other types
        checkPlayableAbility("2: can", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + nethergoyf + " with Escape", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, nethergoyf + " with Escape");
        setChoice(playerA, "Taiga^Grist, the Hunger Tide"); // cards exiled for escape cost

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, nethergoyf, 1);
        assertPowerToughness(playerA, nethergoyf, 0, 1);
        assertExileCount(playerA, 2);
    }

    @Test
    public void test_AI_DynamicGameType() {
        setStrictChooseMode(true);

        addCard(Zone.GRAVEYARD, playerA, nethergoyf);
        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 7);
        addCard(Zone.GRAVEYARD, playerA, "Taiga"); // Land
        addCard(Zone.GRAVEYARD, playerA, "Grist, the Hunger Tide"); // Planeswalker, is a Creature if not in play

        // Nonland permanents you control are artifacts in addition to their other types.
        // The same is true for permanent spells you control and nonland permanent cards you own that aren’t on the battlefield.
        addCard(Zone.HAND, playerA, "Encroaching Mycosynth");

        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, nethergoyf, 1);
        assertPowerToughness(playerA, nethergoyf, 0, 1);
        assertExileCount(playerA, 2);
    }
}
