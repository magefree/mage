package org.mage.test.cards.single.ala;

import mage.abilities.Ability;
import mage.abilities.keyword.DeathtouchAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author awjackson
 */
public class DeathBaronTest extends CardTestPlayerBase {

    private static final String baron = "Death Baron";
    private static final String skeleton = "Drudge Skeletons";
    private static final String zombie = "Scathe Zombies";
    private static final String knight = "Black Knight";

    @Test
    public void testDoesntNormallyAffectSelf() {
        addCard(Zone.BATTLEFIELD, playerA, baron);
        addCard(Zone.BATTLEFIELD, playerA, skeleton);
        addCard(Zone.BATTLEFIELD, playerA, zombie);
        addCard(Zone.BATTLEFIELD, playerA, knight);

        addCard(Zone.BATTLEFIELD, playerB, skeleton);
        addCard(Zone.BATTLEFIELD, playerB, zombie);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        Ability deathtouch = DeathtouchAbility.getInstance();

        // Death Baron doesn't normally affect itself
        assertPowerToughness(playerA, baron, 2, 2);
        assertAbility(playerA, baron, deathtouch, false);

        assertPowerToughness(playerA, skeleton, 2, 2);
        assertPowerToughness(playerA, zombie, 3, 3);
        assertPowerToughness(playerA, knight, 2, 2);
        assertAbility(playerA, skeleton, deathtouch, true);
        assertAbility(playerA, zombie, deathtouch, true);
        assertAbility(playerA, knight, deathtouch, false);

        assertPowerToughness(playerB, skeleton, 1, 1);
        assertPowerToughness(playerB, zombie, 2, 2);
        assertAbility(playerB, skeleton, deathtouch, false);
        assertAbility(playerB, zombie, deathtouch, false);
    }

    @Test
    public void testBecomeSkeleton() {
        addCard(Zone.BATTLEFIELD, playerA, baron);
        addCard(Zone.BATTLEFIELD, playerA, "Amoeboid Changeling");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Target creature gains", baron);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // If you manage to turn it into a Skeleton, however, then it will give itself +1/+1 and deathtouch
        assertPowerToughness(playerA, baron, 3, 3);
        assertAbility(playerA, baron, DeathtouchAbility.getInstance(), true);
    }
}
