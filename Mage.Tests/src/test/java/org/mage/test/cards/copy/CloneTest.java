package org.mage.test.cards.copy;

import java.util.Iterator;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectsList;
import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class CloneTest extends CardTestPlayerBase {

    /**
     * Tests triggers working on both sides after Clone coming onto battlefield
     */
    @Test
    public void testCloneTriggered() {
        addCard(Zone.BATTLEFIELD, playerA, "Bloodgift Demon", 1);

        addCard(Zone.HAND, playerB, "Clone");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Clone");

        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 18);
        assertLife(playerB, 19);

        // 1 from draw steps + 2 from Demon
        assertHandCount(playerA, 3);
        // 2 from draw steps + 1 from Demon
        assertHandCount(playerB, 3);

        assertPermanentCount(playerA, "Bloodgift Demon", 1);
        assertPermanentCount(playerB, "Bloodgift Demon", 1);
    }

    /**
     * Tests Clone is sacrificed and only one effect is turned on
     */
    @Test
    public void testCloneSacrifice() {
        addCard(Zone.BATTLEFIELD, playerA, "Bloodgift Demon", 1);

        addCard(Zone.HAND, playerA, "Diabolic Edict");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        addCard(Zone.HAND, playerB, "Clone");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Clone");
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Diabolic Edict", playerB);

        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Bloodgift Demon", 1);
        assertGraveyardCount(playerA, "Diabolic Edict", 1);
        assertPermanentCount(playerB, "Bloodgift Demon", 0);
        assertGraveyardCount(playerB, "Clone", 1);

        // 1 from draw steps + 2 from Demon
        assertHandCount(playerA, 3);
        // 2 from draw steps + no from Demon (should be sacrificed)
        assertHandCount(playerB, 2);

        assertLife(playerA, 18);
        assertLife(playerB, 20);
    }

    @Test
    public void testCard3() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Zone.HAND, playerA, "Public Execution");
        addCard(Zone.HAND, playerA, "Clone");

        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves");
        addCard(Zone.BATTLEFIELD, playerB, "Craw Wurm");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Public Executio", "Llanowar Elves");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Clone");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Llanowar Elves", 0);
        assertPowerToughness(playerB, "Craw Wurm", 4, 4);
        assertPowerToughness(playerA, "Craw Wurm", 6, 4);
    }

    // copy Nightmare test, check that the P/T setting effect ends
    // if the clone leaves battlefield

    @Test
    public void testCopyNightmare() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 6);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 1);
        // Target creature you control gets +1/+1 and gains hexproof until end of turn. (It can't be the target of spells or abilities your opponents control.)
        addCard(Zone.HAND, playerB, "Ranger's Guile");

        addCard(Zone.HAND, playerA, "Clone");
        // Return target nonland permanent to its owner's hand.
        addCard(Zone.HAND, playerA, "Disperse");

        addCard(Zone.BATTLEFIELD, playerB, "Nightmare", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clone");
        setChoice(playerA, "Nightmare");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, "Ranger's Guile", "Nightmare");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Disperse", "Nightmare");

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerB, "Nightmare", 1);
        assertPowerToughness(playerB, "Nightmare", 6, 6);
        assertPermanentCount(playerA, "Nightmare", 0);
        assertHandCount(playerA, "Disperse", 0);

        FilterCard filter = new FilterCard();
        filter.add(new NamePredicate("Clone"));
        Card card = playerA.getHand().getCards(filter, currentGame).iterator().next();
        if (card != null) {
            Assert.assertEquals("Power has to be 0 because copy from nightmare P/T ability may no longer be applied", 0, card.getPower().getValue());
        }

        Logger.getLogger(CloneTest.class).debug("EXISTING CONTINUOUS EFFECTS:");
        for (ContinuousEffectsList effectsList : currentGame.getContinuousEffects().allEffectsLists) {
            Iterator it = effectsList.iterator();
            while (it.hasNext()) {
                 ContinuousEffect effect = (ContinuousEffect) it.next();
                 Logger.getLogger(CloneTest.class).debug("- " + effect.toString());
            }
        }
    }
            
}
