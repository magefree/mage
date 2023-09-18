package org.mage.test.cards.single.afc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.n.Nihiloor Nihiloor}
 * {2}{W}{U}{B}
 * Legendary Creature — Horror
 * When Nihiloor enters the battlefield, for each opponent, tap up to one untapped creature you control. When you do, gain control of target creature that player controls with power less than or equal to the tapped creature’s power for as long as you control Nihiloor.
 * Whenever you attack with a creature an opponent owns, you gain 2 life and that player loses 2 life.
 * 3/5
 *
 * @author Alex-Vasile
 */
public class NihiloorTest extends CardTestPlayerBase {

    private static final String nihiloor = "Nihiloor";

    /**
     * Reported bug: https://github.com/magefree/mage/issues/9223
     *
     * Nihiloor is used to gain control of Iroas, God of Victory.
     * Once under Nihiloor's controller's control it stops being a creature because devotion is too low.
     * When Nihiloor leaves the battlefield, Iroas is not returned to its previous controller.
     */
    @Test
    public void returnsControlOfPermenantThatChangedTypes() {
        // {2}{R}{W}
        // Legendary Enchantment Creature — God
        // 7/4
        // As long as your devotion to red and white is less than seven, Iroas isn’t a creature.
        // Creatures you control have menace.
        // Prevent all damage that would be dealt to attacking creatures you control.
        String iroas = "Iroas, God of Victory";
        // 8/8
        // Only needed to tap with Nihiloor to get Iroas
        String mammoth = "Aggressive Mammoth";
        // {2}{R}{R}{R}
        // Only needed to have Devotion above 7 so that Iroas is a creature
        String beserker = "Aerathi Berserker";
        // {2}{W}
        // Destroy target creature
        String afterlife = "Afterlife";

        addCard(Zone.HAND, playerA, nihiloor);
        addCard(Zone.HAND, playerA, afterlife);
        addCard(Zone.BATTLEFIELD, playerA, mammoth);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1 + 2 + 3);

        addCard(Zone.BATTLEFIELD, playerB, iroas);
        addCard(Zone.BATTLEFIELD, playerB, beserker, 2); // Total R/W devotion of 8 (6 from the beserkers 2 from iroas)

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, nihiloor);
        setChoice(playerA, mammoth);
        addTarget(playerA, iroas);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertPermanentCount(playerA, nihiloor, 1);
        assertPermanentCount(playerA, iroas,1);

        assertPermanentCount(playerB, iroas, 0);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, afterlife, nihiloor);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertGraveyardCount(playerA, nihiloor, 1);
        assertPermanentCount(playerA, nihiloor, 0);
        assertPermanentCount(playerA, iroas, 0);

        assertPermanentCount(playerB, iroas, 1);
    }
}
