package org.mage.test.cards.single.blb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author notgreat
 */
public class JackdawSaviorTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.j.JackdawSavior Jackdaw Savior} {2}{W}
     * Creature — Bird Cleric
     * Flying
     * Whenever Jackdaw Savior or another creature you control with flying dies,
     * return another target creature card with lesser mana value from your graveyard to the battlefield.
     * <p>
     * This card is unusual in that "another" here refers to "other than the creature that just died",
     * which xmage does not natively support.
     */

    @Test
    public void test_Simultaneous() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Jackdaw Savior"); //MV = 3, flying
        addCard(Zone.BATTLEFIELD, playerA, "Air Elemental"); //MV = 5, flying
        addCard(Zone.BATTLEFIELD, playerA, "Memnite"); //MV = 1

        addCard(Zone.HAND, playerA, "Damnation");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Damnation");
        setChoice(playerA, ""); //Order triggers
        addTarget(playerA, "Jackdaw Savior");
        addTarget(playerA, "Memnite");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, 2); //Air Elemental+Damnation
        assertPermanentCount(playerA, "Jackdaw Savior", 1);
        assertPermanentCount(playerA, "Memnite", 1);
    }

    @Test
    public void test_Clones() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Jackdaw Savior"); //MV = 3, flying
        addCard(Zone.BATTLEFIELD, playerA, "Air Elemental"); //MV = 5, flying
        addCard(Zone.BATTLEFIELD, playerA, "Spidersilk Armor"); //+0/+1 to all

        addCard(Zone.HAND, playerA, "Phantasmal Image", 2); //MV = 2, clone
        addCard(Zone.HAND, playerA, "Murder", 2);

        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 10);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phantasmal Image");
        setChoice(playerA, true);
        setChoice(playerA, "Air Elemental");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder");
        addTarget(playerA, "Air Elemental[only copy]");
        // Can't target itself, no valid targets
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phantasmal Image");
        setChoice(playerA, true);
        setChoice(playerA, "Air Elemental");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder");
        addTarget(playerA, "Air Elemental[only copy]");
        addTarget(playerA, "Phantasmal Image"); // Target the previous one
        setChoice(playerA, false); //Don't copy, stay on battlefield as 0/1

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Murder", 2);
        assertGraveyardCount(playerA, "Phantasmal Image", 1);
        assertPermanentCount(playerA, "Phantasmal Image", 1);
        assertPermanentCount(playerA, "Jackdaw Savior", 1);
        assertPermanentCount(playerA, "Air Elemental", 1);
    }
}
