

package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class ConvokeTest extends CardTestPlayerBase {

    /*
    Test are set to Ignore because the new way to handle this alternate mana payment methods 
    are not supported yet from AI and getPlayable logic.
    */

    @Test
    @Ignore
    public void testConvokeTwoCreatures() {
        /**
         * Ephemeral Shields   {1}{W}
         * Instant
         * Convoke (Your creatures can help cast this spell. Each creature you tap while casting this spell pays for or one mana of that creature's color.)
         * Target creature gains indestructible until end of turn. (Damage and effects that say "destroy" don't destroy it.)
         */

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2); // must be added because getPlayable does not take Convoke into account
        
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Oreskos Swiftclaw", 1);

        addCard(Zone.HAND, playerA, "Ephemeral Shields");

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt");


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ephemeral Shields", "Silvercoat Lion");
        setChoice(playerA, "Yes");
        addTarget(playerA, "Silvercoat Lion^Oreskos Swiftclaw");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Silvercoat Lion");
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerB, "Lightning Bolt", 1);

        assertGraveyardCount(playerA, "Ephemeral Shields", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1); // was indestructible
        assertPermanentCount(playerA, "Oreskos Swiftclaw", 1);

        for (Permanent permanent: currentGame.getBattlefield().getAllActivePermanents(new FilterLandPermanent(), playerA.getId(), currentGame)) {
            Assert.assertTrue(permanent.getName() + " may not be tapped", !permanent.isTapped());
        }
    }


    @Test
    @Ignore
    public void testConvokeTwoCreaturesOneWithProtection() {
        /**
         * Ephemeral Shields   {1}{W}
         * Instant
         * Convoke (Your creatures can help cast this spell. Each creature you tap while casting this spell pays for or one mana of that creature's color.)
         * Target creature gains indestructible until end of turn. (Damage and effects that say "destroy" don't destroy it.)
         */

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2); // must be added because getPlayable does not take Convoke into account

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Black Knight", 1);

        addCard(Zone.HAND, playerA, "Ephemeral Shields");

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt");


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ephemeral Shields", "Silvercoat Lion");
        setChoice(playerA, "Yes");
        addTarget(playerA, "Silvercoat Lion^Black Knight");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Silvercoat Lion");
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerB, "Lightning Bolt", 1);

        assertGraveyardCount(playerA, "Ephemeral Shields", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1); // was indestructible
        assertPermanentCount(playerA, "Black Knight", 1);
        assertTapped("Silvercoat Lion", true);
        assertTapped("Black Knight", true);

        for (Permanent permanent: currentGame.getBattlefield().getAllActivePermanents(new FilterLandPermanent(), playerA.getId(), currentGame)) {
            Assert.assertTrue(permanent.getName() + " may not be tapped", !permanent.isTapped());
        }
    }

    @Test
    @Ignore
    public void testConvokeFromChiefEngineer() {
        /**
         * Chief Engineer   {1}{U}
         * Creature - Vedalken, Artificer
         * Artifact spells you cast have convoke.
         */

        // THIS TEST IS NOT FINISHED
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2); 
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1); // creatures to use for convoek
        addCard(Zone.BATTLEFIELD, playerA, "Chief Engineer", 1);
        
        addCard(Zone.HAND, playerA, "ARTIFACT TO CAST", 1);


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "ARTIFACT TO CAST");
        setChoice(playerA, "Yes");
        addTarget(playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);


    }

}