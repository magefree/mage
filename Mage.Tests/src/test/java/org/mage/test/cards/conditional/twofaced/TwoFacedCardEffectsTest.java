package org.mage.test.cards.conditional.twofaced;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class TwoFacedCardEffectsTest extends CardTestPlayerBase {

    /**
     * Tests that effects disappears when card gets transformed
     */
    @Test
    public void testEffectTurnedOffOnTransform() {
        // Other Human creatures you control get +1/+1.
        // At the beginning of each upkeep, if no spells were cast last turn, transform Mayor of Avabruck.        
        // Howlpack Alpha (transformed side) ----------------
        // Each other creature you control that's a Werewolf or a Wolf gets +1/+1.
        // At the beginning of your end step, put a 2/2 green Wolf creature token onto the battlefield.
        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Howlpack Alpha.
        addCard(Zone.BATTLEFIELD, playerA, "Mayor of Avabruck");
        addCard(Zone.BATTLEFIELD, playerA, "Wolfir Avenger");
        addCard(Zone.BATTLEFIELD, playerA, "Elite Inquisitor");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        // check was transformed
        assertPermanentCount(playerA, "Howlpack Alpha", 1);

        // check new effect works
        assertPowerToughness(playerA, "Wolfir Avenger", 4, 4, Filter.ComparisonScope.Any);

        // check old effect doesn't work
        Permanent eliteInquisitor = getPermanent("Elite Inquisitor", playerA.getId());
        Assert.assertEquals(2, eliteInquisitor.getPower().getValue());
        Assert.assertEquals(2, eliteInquisitor.getToughness().getValue());
    }

    /**
     * Tests copying card with transform
     */
    @Test
    public void testCopyCardWithTransform() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, "Mayor of Avabruck");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Zone.HAND, playerB, "Clone");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mayor of Avabruck");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Clone");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 0);
        assertHandCount(playerB, 1);

        assertPermanentCount(playerA, "Mayor of Avabruck", 1);
        assertPermanentCount(playerB, "Mayor of Avabruck", 1);
    }

    /**
     * Tests copied card should NOT be possible to transform
     */
    @Test
    public void testCopyCantTransform() {
        // At the beginning of each upkeep, if no spells were cast last turn, transform Mayor of Avabruck.
        addCard(Zone.HAND, playerA, "Mayor of Avabruck"); // {1}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        //
        // You may have Clone enter the battlefield as a copy of any creature on the battlefield.
        addCard(Zone.HAND, playerB, "Clone"); // {3}{U}
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mayor of Avabruck");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Clone");
        setChoice(playerB, true); // use copy
        setChoice(playerB, "Mayor of Avabruck"); // clone target

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 2);
        assertHandCount(playerB, 2);

        // should transform - original
        assertPermanentCount(playerA, "Howlpack Alpha", 1);
        // should not transform - copy
        assertPermanentCount(playerB, "Mayor of Avabruck", 1);
    }

    /**
     * Tests copying already transformed card
     */
    @Test
    public void testCopyAlreadyTransformedCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Mayor of Avabruck");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Zone.HAND, playerB, "Clone");

        // copy already transformed
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Clone");
        playerB.addChoice("Howlpack Alpha");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 0);
        assertHandCount(playerB, 1);

        // should transform - original
        assertPermanentCount(playerA, "Howlpack Alpha", 1);
        // check copying card is also transformed
        assertPermanentCount(playerB, "Howlpack Alpha", 1);
    }

    /**
     * Tests that triggered abilities of the frontside do not trigger if the card is transformed
     */
    @Test
    public void testTransformedDOesNotTriggerFrontsideAbilities() {
        addCard(Zone.BATTLEFIELD, playerA, "Loyal Cathar");

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Loyal Cathar");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Unhallowed Cathar");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, "Lightning Bolt", 2);
        assertPermanentCount(playerA, "Unhallowed Cathar", 0);
        assertGraveyardCount(playerA, "Loyal Cathar", 1);
    }
}
