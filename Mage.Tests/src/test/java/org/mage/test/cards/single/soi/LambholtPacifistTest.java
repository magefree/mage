package org.mage.test.cards.single.soi;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Lambholt Pacifist {1}{G}
 * 3/3
 * Creature — Human Shaman Werewolf
 * Lambholt Pacifist can’t attack unless you control a creature with power 4 or greater.
 * At the beginning of each upkeep, if no spells were cast last turn, transform Lambholt Pacifist.
 *
 * @author escplan9
 */
public class LambholtPacifistTest extends CardTestPlayerBase {

    private final String lambholt = "Lambholt Pacifist";

    /**
     * An uncrewed vehicle does not count as a creature.
     */
    @Test
    public void uncrewedVehicle_LambholtCannotAttack() {

        /*
         Heart of Kiran {2}
         4/4
         Legendary Artifact - Vehicle
         Flying, vigilance
         Crew 3 (Tap any number of creatures you control with total power 3 or more:
                 This Vehicle becomes an artifact creature until end of turn.)
         You may remove a loyalty counter from a planeswalker you control rather than pay Heart of Kiran's crew cost.
         */
        String heartKiran = "Heart of Kiran";
        // Just needs something to cast to prevent lambholt transforming
        String orni = "Ornithopter"; // {0} 0/2 flyer
        
        addCard(Zone.HAND, playerA, lambholt);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, heartKiran);
        addCard(Zone.HAND, playerB, orni);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lambholt);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, orni);

        attack(3, playerA, lambholt);
        
        setStopAt(3, PhaseStep.END_COMBAT);

        try {
            execute();
        } catch (Throwable e) {
            if (!e.getMessage().contains("Player PlayerA must have 0 actions but found 1")) {
                Assert.fail("Should have had error about not being able to attack, but got:\n" + e.getMessage());
            }
        }
        
        assertPermanentCount(playerB, orni, 1);
        assertTapped(lambholt, false);
        assertLife(playerB, 20);        
    }
}
