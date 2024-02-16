package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9
 */
public class ChildOfAlaraTest extends CardTestPlayerBase {

    @Test
    public void testChildOfAlaraOblivionRingInteraction() {
            /*
            Child of Alara {W}{U}{B}{R}{G}
             Legendary Creature — Avatar 6/6
            Trample
            When Child of Alara dies, destroy all nonland permanents. They can't be regenerated.
            */
            String childAlara = "Child of Alara";

            /*
            Oblivion Ring {2}{W}
            Enchantment
            When Oblivion Ring enters the battlefield, exile another target nonland permanent.
            When Oblivion Ring leaves the battlefield, return the exiled card to the battlefield under its owner's control.
            */
            String oRing = "Oblivion Ring";

            /*
            Tamiyo, Field Research {1}{G}{W}{U}
            Planeswalker - Tamiyo
            +1: Choose up to two target creatures. Until your next turn, whenever either of those creatures deals combat damage, you draw a card.
            −2: Tap up to two target nonland permanents. They don't untap during their controller's next untap step.
            −7: Draw three cards. You get an emblem with "You may cast spells from your hand without paying their mana costs."
            */
            String tamiyo = "Tamiyo, Field Researcher";

            String memnite = "Memnite"; // {0} 1/1
            String hGiant = "Hill Giant"; // {3}{R} 3/3
            String dDemolition = "Daring Demolition"; // {2}{B}{B} sorcery: destroy target creature or vehicle
            
            addCard(Zone.HAND, playerA, tamiyo);
            addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
            addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
            addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
            addCard(Zone.BATTLEFIELD, playerA, hGiant);
            addCard(Zone.BATTLEFIELD, playerA, childAlara);
            
            addCard(Zone.HAND, playerB, dDemolition);	
            addCard(Zone.HAND, playerB, oRing);
            addCard(Zone.BATTLEFIELD, playerB, "Swamp", 4);
            addCard(Zone.BATTLEFIELD, playerB, "Plains", 4);
            addCard(Zone.BATTLEFIELD, playerB, memnite);

            castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, tamiyo);
            castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, oRing);
            addTarget(playerB, tamiyo);
            waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);
            castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, dDemolition, childAlara);
            
            setStopAt(2, PhaseStep.BEGIN_COMBAT);
            execute();
            
            assertGraveyardCount(playerB, dDemolition, 1);
            assertGraveyardCount(playerA, childAlara, 1);
            assertGraveyardCount(playerB, oRing, 1); // destroyed by child
            assertGraveyardCount(playerB, memnite, 1);
            assertGraveyardCount(playerA, hGiant, 1);
            assertPermanentCount(playerA, tamiyo, 1); // o-ring destroyed returns Tamiyo to battlefield
    }
}
