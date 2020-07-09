
package org.mage.test.cards.abilities.other;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author jeffwadsworth
 */
public class AuratouchedMageTest extends CardTestPlayerBase {

    /**
     * Auratouched Mage Creature — Human Wizard 3/3, 5W When Auratouched Mage
     * enters the battlefield, search your library for an Aura card that could
     * enchant it. If Auratouched Mage is still on the battlefield, put that
     * Aura card onto the battlefield attached to it. Otherwise, reveal the Aura
     * card and put it into your hand. Then shuffle your library.
     *
     */
    
    //If someone knows the way to elegantly handle the test mechanism in regards to no valid targets, please modify.  The test works fine in practice.
    @Ignore
    public void testAuratouchedMageEffectHasMadeIntoTypeArtifact() {
        //Any Aura card you find must be able to enchant Auratouched Mage as it currently exists, or as it most recently existed on the battlefield if it's no 
        //longer on the battlefield. If an effect has made the Mage an artifact, for example, you could search for an Aura with “enchant artifact.”
        //Expected result: An effect has made Auratouched Mage into an artifact upon entering the battlefield.  An aura that only works on artifacts should work.
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        addCard(Zone.HAND, playerA, "Auratouched Mage"); //5W cost
        addCard(Zone.HAND, playerA, "Argent Mutation"); //2U cost.  Target is an artifact until end of turn
        addCard(Zone.LIBRARY, playerA, "Relic Ward"); //Only enchants an artifact permanent

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Auratouched Mage");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Argent Mutation", "Auratouched Mage");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        execute();

        assertPermanentCount(playerA, "Auratouched Mage", 1);
        assertPermanentCount(playerA, "Relic Ward", 1);

    }

    @Test
    public void testGainsLegalAura() {
        // Expected result: Brainwash gets placed on Auratouched Mage
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        addCard(Zone.HAND, playerA, "Auratouched Mage");
        addCard(Zone.LIBRARY, playerA, "Brainwash");//legal aura for Auratouched Mage

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Auratouched Mage");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        execute();

        assertPermanentCount(playerA, "Auratouched Mage", 1);
        assertPermanentCount(playerA, "Brainwash", 1);

    }

     //If someone knows the way to elegantly handle the test mechanism in regards to no valid targets, please modify.  The test works fine in practice.
    @Test
    public void testAuratouchedMageNotOnBattlefield() {
        // Expected result: Auratouched Mage is exiled immediately after entering the battlefield, the legal aura (Brainwash) gets put into controller's hand
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        addCard(Zone.HAND, playerA, "Auratouched Mage");
        addCard(Zone.HAND, playerA, "Swords to Plowshares"); //exiles Auratouched Mage
        addCard(Zone.LIBRARY, playerA, "Brainwash"); //valid aura for Auratouched Mage
        addCard(Zone.LIBRARY, playerA, "Animate Wall"); //not a valid aura for the Auratouched Mage

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Auratouched Mage");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Swords to Plowshares", "Auratouched Mage");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        execute();

        assertPermanentCount(playerA, "Auratouched Mage", 0);
        assertPermanentCount(playerA, "Brainwash", 0);
        assertHandCount(playerA, "Brainwash", 1);
        assertLibraryCount(playerA, "Animate Wall", 1);

    }
}
